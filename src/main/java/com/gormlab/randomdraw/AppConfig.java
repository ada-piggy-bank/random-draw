package com.gormlab.randomdraw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class AppConfig {
    @Bean
    public Random randomBean() {
        return new Random();
    }
}
