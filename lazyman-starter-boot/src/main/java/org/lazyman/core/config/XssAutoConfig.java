package org.lazyman.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.lazyman.core.xss.XssFilter;
import org.lazyman.core.xss.XssStringJsonDeserializer;
import org.lazyman.core.xss.XssStringJsonSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
@ConditionalOnProperty(name = "xss.enable", havingValue = "true")
public class XssAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public XssFilter xssFilter() {
        return new XssFilter();
    }

    @Bean
    @Primary
    public ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule xssSerializerModule = new SimpleModule("XssStringJsonSerializer");
        xssSerializerModule.addSerializer(String.class, new XssStringJsonSerializer());
        objectMapper.registerModule(xssSerializerModule);
        SimpleModule xssDeserializerModule = new SimpleModule("XssStringJsonDeserializer");
        xssDeserializerModule.addDeserializer(String.class, new XssStringJsonDeserializer());
        objectMapper.registerModule(xssDeserializerModule);
        return objectMapper;
    }
}
