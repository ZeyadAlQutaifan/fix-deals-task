package com.progressoft.app.config;


import com.progressoft.validate.DealInputValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public DealInputValidator dealInputValidator() {
        return new DealInputValidator();
    }
}
