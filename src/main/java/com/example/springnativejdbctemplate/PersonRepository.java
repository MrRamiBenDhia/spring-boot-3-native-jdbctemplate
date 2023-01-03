package com.example.springnativejdbctemplate;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
