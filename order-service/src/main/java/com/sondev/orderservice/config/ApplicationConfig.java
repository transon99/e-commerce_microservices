package com.sondev.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class ApplicationConfig {
    @Bean
    JsonMessageConverter converter(){
        return new JsonMessageConverter();
    }
}
