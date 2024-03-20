package com.example.demo.dto;

import com.example.demo.model.Client;
import com.example.demo.model.Phone;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class ClientDto {
    private Long id;

    private String name;

    private String address;

    private String phones;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = Objects.nonNull(client.getAddress()) ? client.getAddress().getStreet() : "";
        this.phones = CollectionUtils.isEmpty(client.getPhones()) ? "" :
                client.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining(" , "));
    }

    public ClientDto() {
    }
}
