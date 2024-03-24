package ru.otus.servlet;


import lombok.Getter;
import ru.otus.hibernate.crm.model.Client;
import ru.otus.hibernate.crm.model.Phone;

import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class PojoClient {
    private final long id;

    private final String name;

    private final String address;

    private final String phones;

    public PojoClient(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = Objects.nonNull(client.getAddress()) ? client.getAddress().getStreet() : "";
        this.phones = client.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining(", "));

    }

}
