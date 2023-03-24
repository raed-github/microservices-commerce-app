package com.rsaad.orderservice.config;

import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class FeignConfiguration {
    public FeignConfiguration() {
        Properties properties = System.getProperties();
        properties.setProperty("feign.circuitbreaker.enabled", "true");
    }
}

