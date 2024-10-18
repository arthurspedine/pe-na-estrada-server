package com.penaestrada.controller;

import com.penaestrada.dto.CreateWorkshop;
import com.penaestrada.dto.WorkshopDetailsResponse;
import com.penaestrada.model.User;
import com.penaestrada.model.Workshop;
import com.penaestrada.service.UserService;
import com.penaestrada.service.WorkshopService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/workshop")
public class WorkshopController {

    @Autowired
    private WorkshopService workshopService;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @Transactional
    public ResponseEntity<WorkshopDetailsResponse> createWorkshopAccount(@RequestBody @Valid CreateWorkshop data) {
        User user = userService.createUser(data.login());
        Workshop workshop = workshopService.createWorkshop(data, user);
        WorkshopDetailsResponse response = new WorkshopDetailsResponse(
                workshop.getId(), workshop.getName(), workshop.getAddress(),
                String.valueOf(workshop.getNumber()), workshop.getZipCode(),
                workshop.getNeighborhood(), workshop.getCity(), workshop.getState(),
                workshop.getRating(), workshop.getMapsUrl(), new ArrayList<>()
        );
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<WorkshopDetailsResponse>> getAllWorkshops() {
        return ResponseEntity.ok(workshopService.getAllWorkshops());
    }
}
