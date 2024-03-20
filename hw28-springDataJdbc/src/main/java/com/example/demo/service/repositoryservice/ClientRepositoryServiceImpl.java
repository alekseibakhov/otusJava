package com.example.demo.service.repositoryservice;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientRepositoryServiceImpl implements ClientRepositoryService {
    private final ClientRepository clientRepository;

    public ClientRepositoryServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
}
