package com.rookies5.myspringbootlab.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class ProdConfig {
    @Bean
    public CustomerVO customVO() {
        return CustomerVO.builder()
                .mode("운영 환경")
                .rate(0.8)
                .build();
    }
}
