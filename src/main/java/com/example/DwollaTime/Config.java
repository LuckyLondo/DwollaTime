package com.example.DwollaTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private ObjectMapper objectMapper;

    public Config() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return this.objectMapper;
    }


}
