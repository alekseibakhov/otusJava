package com.example.demo.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "address")
public class Address {

    @Id
    private final Long id;
    private final Long clientId;
    private final String street;

    @PersistenceCreator
    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Address(String street) {
        this(null, street, null);
    }
}
