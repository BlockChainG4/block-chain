package com.g4.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptEngineManager;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableRetry
@EnableAsync
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

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("BroadcastResult-");
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlockChainApplication.class, args);
    }
}
