package com.penaestrada.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Client")
@Table(name = "t_pe_clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "client")
    private List<ClientAddress> addresses;

    @OneToMany(mappedBy = "client")
    private List<ContactPhone> contactPhones;

    @OneToMany(mappedBy = "client")
    private List<Vehicle> vehicles;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf.replace("-", "").replaceAll("[^\\d]", "");
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        } else if (birthDate.isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("Client must be at least 18 years old");
        }
        this.birthDate = birthDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ClientAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<ClientAddress> addresses) {
        this.addresses = addresses;
    }

    public List<ContactPhone> getContactPhones() {
        return contactPhones;
    }

    public void setContactPhones(List<ContactPhone> contactPhones) {
        this.contactPhones = contactPhones;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
