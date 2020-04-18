package com.app;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;


import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.persistence.EntityManagerFactory;
import java.util.TimeZone;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class CinemaRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaRestApiApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public PlatformTransactionManager transactionManager()
    {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
