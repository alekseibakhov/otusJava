package com.example.demo.service;

import com.example.demo.dto.ClientDto;
import com.example.demo.model.Address;
import com.example.demo.model.Client;
import com.example.demo.model.Phone;
import com.example.demo.service.repositoryservice.ClientRepositoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepositoryService clientRepository;

    public ClientServiceImpl(ClientRepositoryService clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public void saveClient(ClientDto client) {
        Address address = new Address(client.getAddress());
        Set<Phone> phones = Arrays.stream(client.getPhones().split(","))
                .map(phone -> new Phone(phone, null))
                .collect(Collectors.toSet());

        Client newClient = new Client(null, client.getName(), address, phones);
        clientRepository.save(newClient);

    }
}
