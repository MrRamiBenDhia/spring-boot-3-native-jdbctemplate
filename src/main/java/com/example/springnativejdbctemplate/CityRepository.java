package com.example.springnativejdbctemplate;

import org.springframework.data.repository.ListCrudRepository;

public interface CityRepository extends ListCrudRepository<City, String> {
}
