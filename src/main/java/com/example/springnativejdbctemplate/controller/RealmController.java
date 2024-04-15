package com.example.springnativejdbctemplate.controller;


import com.example.springnativejdbctemplate.Service.RealmService;
import com.example.springnativejdbctemplate.model.Realm;
import com.example.springnativejdbctemplate.repository.RealmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realm")
@Controller
public class RealmController {

    @Autowired
    RealmService realmService;


    @PutMapping("/{idRealm}/assignUser/{idUser}/")//? assign User to Realm
    public void assignUser(@PathVariable Long idRealm, @PathVariable Long idUser){


        System.out.println("~~~~~~~~~~~");
        System.out.println(idRealm);
        System.out.println("~~~~~~~~~~~");
        realmService.addUser(idRealm,idUser);
    }

    @PutMapping("/{idRealm}/assignClient/{idClient}/")//? assign Client to Realm
    public void assignClient(@PathVariable Long idRealm, @PathVariable Long idClient){


        System.out.println("~~~~~~~~~~~");
        System.out.println(idRealm);
        System.out.println("~~~~~~~~~~~");
        realmService.addClient(idRealm,idClient);
    }



    @GetMapping
    public List<Realm> getAllRealm() {
        return realmService.findAll();
    }
    @GetMapping("/{id}")
    public Realm getRealmById(@PathVariable("id") Long id) {
        System.out.println("!~~~~~~~~~~~~~~~~~~~!");
        System.out.println(id);
        System.out.println("!~~~~~~~~~~~~~~~~~~~!");

        return realmService.findByID(id).orElse(new Realm());
    }

    @PostMapping
    public Realm createRealm(@RequestBody Realm realm) {
        return realmService.addRealm(realm);
    }

    @PatchMapping("/{id}")

    public Realm updateRealm(@PathVariable("id") Long id, @RequestBody Realm realm) {
        if(realm.getID()==null){
            realm.setID(id);
        }
        return realmService.updateRealm(realm);
    }

    @DeleteMapping("/{id}")
    public void deleteRealm(@PathVariable("id") Long id){
        realmService.deleteRealm(id);

    }

}
