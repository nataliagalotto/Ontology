package com.projeto.generico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        return true;
    }
}
