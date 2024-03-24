package com.example.demo.service.repositoryservice;

import com.example.demo.model.Client;

import java.util.List;

public interface ClientRepositoryService {
    List<Client> findAll();

    void save(Client client);
}
