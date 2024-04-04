package com.example.springnativejdbctemplate.repository;

import com.example.springnativejdbctemplate.model.Realm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealmRepo extends CrudRepository<Realm,Long> {
    Realm findRealmsByID(Long ID);

    List<Realm> findAll();
}
