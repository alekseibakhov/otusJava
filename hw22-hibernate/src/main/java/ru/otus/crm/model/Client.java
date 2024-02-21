package ru.otus.crm.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "client")
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
        this.phones = new ArrayList<>();
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.phones = new ArrayList<>();
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = new ArrayList<>(phones);
        this.phones.forEach(ph -> ph.setClient(this));
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        var cloneClient = new Client(this.id, this.name);

        cloneClient.setAddress(this.address != null ? this.address.clone() : null);
        this.phones.forEach(ph -> cloneClient.phones.add(new Phone(ph.getId(), ph.getNumber(), cloneClient)));

        return cloneClient;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
