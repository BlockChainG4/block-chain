package com.g4.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptEngineManager;

@SpringBootApplication
@EnableRetry
public class BlockChainApplication {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    ScriptEngineManager scriptEngineManager() {
        return new ScriptEngineManager();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlockChainApplication.class, args);
    }
}
