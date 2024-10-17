package com.penaestrada.model;

import jakarta.persistence.*;

@Entity(name = "ContactPhone")
@Table(name = "t_pe_contact_phones")
public class ContactPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ddi;
    @Column(name = "phone", nullable = false)
    private Integer phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workshop_id", referencedColumnName = "id")
    private Workshop workshop;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public ContactPhone() {
    }

    public String formattedNumber() {
        String formattedNumber;
        String number = String.valueOf(getPhoneNumber());
        if (number.length() == 9) {
            formattedNumber = number.substring(0, 5) + "-" + number.substring(5);
        } else if (number.length() == 8) {
            formattedNumber = number.substring(0, 4) + "-" + number.substring(4);
        } else {
            formattedNumber = number;
        }
        return "(" + ddi + ") " + formattedNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDdi() {
        return ddi;
    }

    public void setDdi(Integer ddi) {
        this.ddi = ddi;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
