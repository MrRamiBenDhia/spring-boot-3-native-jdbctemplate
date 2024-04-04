package com.example.springnativejdbctemplate.repository;

import com.example.springnativejdbctemplate.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends CrudRepository<Client, Long> {
    Client findClientByID(Long ID);

    List<Client> findAll();
}
