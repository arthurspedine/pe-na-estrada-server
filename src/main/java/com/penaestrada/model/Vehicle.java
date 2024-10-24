package com.penaestrada.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Vehicle")
@Table(name = "t_pe_vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String brand;

    @Column(nullable = false, length = 40)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(name = "license_plate", nullable = false, length = 7)
    private String licensePlate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "vehicle")
    private List<Estimate> estimates;


    public Vehicle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate.replace("-", "");
    }

    public List<Estimate> getEstimates() {
        return estimates;
    }
}
