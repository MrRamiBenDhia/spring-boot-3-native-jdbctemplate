package com.example.springnativejdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringNativeJdbctemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNativeJdbctemplateApplication.class, args);
    }

    @Bean ApplicationRunner applicationRunner(PersonRepository personRepository) {
        return args -> {
            if (personRepository.findAll().isEmpty()) {
                personRepository.save(new Person("p1", "amy"));
                personRepository.save(new Person("p2", "jane"));
            }
        };
    }

}

record Person(String id, String name){}

@RestController
class PersonController {
    private final PersonRepository personRepository;

    PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/person")
    List<Person> all() {
        return personRepository.findAll();
    }
}

@Component
class PersonRepository {
    private final JdbcTemplate jdbcTemplate;

    PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Person> findAll() {
        return jdbcTemplate.query("select * from person", (rs, rowNum) -> new Person(rs.getString(1), rs.getString(2)));
    }

    void save(Person person) {
        jdbcTemplate.update("insert into person values (?, ?)", person.id(), person.name());
    }
}
