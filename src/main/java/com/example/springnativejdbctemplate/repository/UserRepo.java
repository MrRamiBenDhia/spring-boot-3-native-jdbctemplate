package com.example.springnativejdbctemplate.repository;


import com.example.springnativejdbctemplate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUID(Long UID);

    User findByemail(String email);

    List<User> findAll();
}
