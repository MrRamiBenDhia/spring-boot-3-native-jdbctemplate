package com.example.springnativejdbctemplate.repository;

import com.example.springnativejdbctemplate.model.Client;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findClientByID(Long ID);

    List<Client> findAll();
}
