package com.penaestrada.service;

import com.penaestrada.dto.WorkshopAddressResponse;
import com.penaestrada.dto.ContactResponse;
import com.penaestrada.dto.CreateWorkshop;
import com.penaestrada.dto.WorkshopDetailsResponse;
import com.penaestrada.model.User;
import com.penaestrada.model.Workshop;
import com.penaestrada.repository.WorkshopRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkshopService {

    @Autowired
    private WorkshopRepository repository;

    public Workshop createWorkshop(CreateWorkshop data, User user) {
        Workshop newWorkshop = new Workshop();
        newWorkshop.setName(data.name());
        newWorkshop.setAddress(data.address());
        newWorkshop.setNumber(Integer.parseInt(data.number()));
        newWorkshop.setZipCode(data.zipCode());
        newWorkshop.setNeighborhood(data.neighborhood());
        newWorkshop.setCity(data.city());
        newWorkshop.setState(data.state());
        newWorkshop.setRating(data.rating());
        newWorkshop.setMapsUrl(data.mapsUrl());

        newWorkshop.setUser(user);
        repository.save(newWorkshop);
        return newWorkshop;
    }

    public List<WorkshopDetailsResponse> getAllWorkshops() {
        return repository.findAll().stream()
                .map(this::workshopToResponse)
                .collect(Collectors.toList());
    }

    public Workshop getWorkshopByLogin(String login) {
        return repository.findByUserLogin(login).orElseThrow(() -> new EntityNotFoundException("Workshop not found"));
    }

    public WorkshopDetailsResponse workshopToResponse(Workshop workshop) {
        List<ContactResponse> contacts = workshop.getContactPhones().stream()
                .map(f -> new ContactResponse(f.getId(), f.formattedNumber()))
                .toList();
        return new WorkshopDetailsResponse(
                workshop.getId(), workshop.getName(), new WorkshopAddressResponse(workshop.getAddress(),
                String.valueOf(workshop.getNumber()), workshop.getZipCode(),
                workshop.getNeighborhood(), workshop.getCity(), workshop.getState()),
                workshop.getRating(), workshop.getMapsUrl(), contacts
        );
    }

    public List<Workshop> findAllWorkshops() {
        return repository.findAll();
    }
}
