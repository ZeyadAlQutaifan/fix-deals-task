package com.progressoft.app.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
@Log4j2
public class DateTimeConfig {
    @Value("${spring.jpa.properties.hibernate.jdbc.time_zone}")
    private String defaultTimeZone;

    @Bean
    public String init() {
        TimeZone.setDefault(TimeZone.getTimeZone(defaultTimeZone));
        log.info("Time zone configured to : {}" , defaultTimeZone);
        return "Configure default time zone";
    }
}
