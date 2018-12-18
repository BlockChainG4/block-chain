package com.g4.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
