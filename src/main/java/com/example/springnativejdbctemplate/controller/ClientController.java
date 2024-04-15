package com.example.springnativejdbctemplate.controller;


import com.example.springnativejdbctemplate.Service.ClientService;
import com.example.springnativejdbctemplate.model.Client;
import com.example.springnativejdbctemplate.repository.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@Controller
@RequiredArgsConstructor

public class ClientController {


    private final ClientService clientService;


    @GetMapping
    public List<Client> getAllClient() {
        return clientService.findAll();
    }
    @GetMapping("/{id}")
    public Client getClientById(@PathVariable("id") Long id) {
        return clientService.findByID(id).orElse(new Client());
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @PatchMapping("/{id}")
    public Client updateClient(@PathVariable("id") Long id, @RequestBody Client client) {
        if(client.getID()==null){
            client.setID(id);
        }
        return clientService.updateClient(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable("id") Long id){
        clientService.deleteClient(id);

    }

}
