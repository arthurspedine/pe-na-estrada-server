package com.penaestrada.service;

import com.penaestrada.model.Client;
import com.penaestrada.model.ContactPhone;
import com.penaestrada.model.Workshop;
import com.penaestrada.repository.ContactPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactPhoneService {

    @Autowired
    private ContactPhoneRepository repository;

    public ContactPhone createContactPhone(Client client, String ddi, String number) {
        ContactPhone contactPhone = new ContactPhone();
        contactPhone.setClient(client);
        contactPhone.setDdi(Integer.parseInt(ddi));
        contactPhone.setPhoneNumber(Integer.parseInt(number.replace("-", "")));
        repository.save(contactPhone);
        return contactPhone;
    }

    public ContactPhone createContactPhone(Workshop workshop, String ddi, String number) {
        ContactPhone contactPhone = new ContactPhone();
        contactPhone.setWorkshop(workshop);
        contactPhone.setDdi(Integer.parseInt(ddi));
        contactPhone.setPhoneNumber(Integer.parseInt(number.replace("-", "")));
        repository.save(contactPhone);
        return contactPhone;
    }
}
