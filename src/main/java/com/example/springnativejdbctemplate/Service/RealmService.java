package com.example.springnativejdbctemplate.Service;

import com.example.springnativejdbctemplate.model.Realm;

import java.util.List;
import java.util.Optional;

public interface RealmService {
    void addUser(Long idRealm, Long idUser);
    void addClient(Long idRealm, Long idClient);

        Optional<Realm> findByID(Long ID);
    Realm addRealm(Realm realm);
    Realm updateRealm(Realm realm);
    void deleteRealm(Long idRealm);
    List<Realm> findAll();
}
