package com.penaestrada.service;

import com.penaestrada.dto.ContactResponse;
import com.penaestrada.dto.CreateWorkshop;
import com.penaestrada.dto.WorkshopDetailsResponse;
import com.penaestrada.model.User;
import com.penaestrada.model.Workshop;
import com.penaestrada.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .map(w -> {
                    List<ContactResponse> contacts = w.getContactPhones().stream()
                            .map(f -> new ContactResponse(f.getId(), f.formattedNumber()))
                            .collect(Collectors.toList());

                    return new WorkshopDetailsResponse(
                            w.getId(),
                            w.getName(),
                            w.getAddress(),
                            String.valueOf(w.getNumber()),
                            w.getZipCode(),
                            w.getNeighborhood(),
                            w.getCity(),
                            w.getState(),
                            w.getRating(),
                            w.getMapsUrl(),
                            contacts
                    );
                })
                .collect(Collectors.toList());
    }
}
