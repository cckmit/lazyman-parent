package org.lazyman.core.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

@Configuration
@EnableConfigurationProperties(RestClientProperties.class)
@ConditionalOnProperty(name = "restclient.enable", havingValue = "true")
public class RestClientAutoConfig {
    @Resource
    private RestClientProperties restClientProperties;

    @Bean
    @ConditionalOnMissingBean
    public CloseableHttpClient httpClient() throws Exception {
        SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        return HttpClients.custom()
                .setSSLSocketFactory(factory)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientHttpRequestFactory httpRequestFactory() throws Exception {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient());
        factory.setConnectionRequestTimeout(restClientProperties.getConnectionRequestTimeout());
        factory.setConnectTimeout(restClientProperties.getConnectTimeout());
        factory.setReadTimeout(restClientProperties.getReadTimeout());
        return factory;
    }

    @Bean
    @ConditionalOnBean({ClientHttpRequestFactory.class})
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }
}
