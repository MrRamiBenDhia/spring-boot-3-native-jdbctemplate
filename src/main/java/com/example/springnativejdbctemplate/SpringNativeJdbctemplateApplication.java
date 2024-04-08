package com.example.springnativejdbctemplate;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})

public class SpringNativeJdbctemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNativeJdbctemplateApplication.class, args);
        System.out.println("Hellooo there its working");
    }

}
