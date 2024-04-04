package com.example.springnativejdbctemplate.Service;


import com.example.springnativejdbctemplate.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service()
public interface UserService {

    Optional<User> findByUID(Long UID);
    User findByemail(String email);
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Long userId);
    User save(User user);
    List<User> findAll();
}
