package com.example.firmaplatformasi.SecurityConfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class WrittenBy {
    @Bean
    AuditorAware auditorAware(){return new CreateBy();}
}
