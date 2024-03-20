package com.example.demo.service;

import com.example.demo.dto.ClientDto;
import com.example.demo.model.Client;

import java.util.List;


public interface ClientService {
    List<Client> getAll();

    void saveClient(ClientDto newClient);
}
