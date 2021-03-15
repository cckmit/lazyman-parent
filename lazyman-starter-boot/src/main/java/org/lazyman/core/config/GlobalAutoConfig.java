package org.lazyman.core.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.lazyman.common.util.SpringContextUtils;
import org.lazyman.core.i18n.I18nHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.TimeZone;

@Configuration
@EnableConfigurationProperties(GlobalProperties.class)
@Slf4j
public class GlobalAutoConfig {
    @Resource
    private GlobalProperties globalProperties;

    @Bean
    @ConditionalOnMissingBean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(globalProperties.getTimeZone()));
        log.info("TimeZone have changed to {}", TimeZone.getDefault().getDisplayName());
    }

    @Bean
    @ConditionalOnMissingBean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(globalProperties.isValidFailFast())
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    public I18nHelper i18nHelper(MessageSource messageSource) {
        return new I18nHelper(messageSource);
    }
}
