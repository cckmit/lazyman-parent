package org.lazyman.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lazyman.common.constant.CommonConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.Nullable;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@EnableConfigurationProperties(Swagger2Properties.class)
@ConditionalOnProperty(name = "knife4j.enable", havingValue = "true")
public class Swagger2AutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("1.0版本")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(new Swaggerfilter())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(securityContexts());
    }

    @Bean
    @ConditionalOnMissingBean
    SecurityScheme securityScheme() {
        return new ApiKey(CommonConstant.X_ACCESS_TOKEN, CommonConstant.X_ACCESS_TOKEN, "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("lazyman微服务后台API接口文档")
                .version("1.0.0")
                .description("lazyman微服务后台API接口文档")
                .build();
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList(
                Collections.singleton(SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build())
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList(
                Collections.singleton(new SecurityReference(CommonConstant.X_ACCESS_TOKEN, authorizationScopes)));
    }

    class Swaggerfilter implements Predicate<RequestHandler> {
        @Override
        public boolean apply(@Nullable RequestHandler requestHandler) {
            if (requestHandler.findAnnotation(ApiOperation.class).isPresent()) {
                return true;
            }
            return false;
        }
    }
}
