package com.example.springnativejdbctemplate;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringNativeJdbctemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNativeJdbctemplateApplication.class, args);
    }


    @Bean ApplicationRunner applicationRunner(PersonRepository personRepository, CityRepository cityRepository) {
        return args -> {
            if (personRepository.findAll().isEmpty()) {
                personRepository.save(new Person("p1", "amy"));
                personRepository.save(new Person("p2", "jane"));
            }
            if (cityRepository.count() == 0) {
                cityRepository.save(new City("c1", "Madrid"));
                cityRepository.save(new City("c2", "London"));
            }
        };
    }

}
