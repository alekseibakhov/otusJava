package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.data.repository.ListCrudRepository;

public interface ClientRepository extends ListCrudRepository<Client, Long> {
}