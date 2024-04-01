package com.example.demo.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Table(name = "phone")
public class Phone {

    @Id
    private final Long id;

    private final String number;

    private final Long clientId;

    @PersistenceCreator
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(String number, Long clientId) {
        this(null, number, clientId);
    }
}
