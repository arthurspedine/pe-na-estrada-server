package com.penaestrada.controller;

import com.penaestrada.dto.*;
import com.penaestrada.infra.security.TokenService;
import com.penaestrada.model.Client;
import com.penaestrada.model.ContactPhone;
import com.penaestrada.model.User;
import com.penaestrada.model.Workshop;
import com.penaestrada.model.exception.UndefinedAuthHeaderException;
import com.penaestrada.service.ContactPhoneService;
import com.penaestrada.service.UserService;
import com.penaestrada.service.WorkshopService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ContactPhoneService contactPhoneService;

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<WorkshopDetailsResponse> createWorkshopAccount(@RequestBody @Valid CreateWorkshop data) {
        User user = userService.createUser(data.login());
        Workshop workshop = workshopService.createWorkshop(data, user);
        ContactPhone contactPhone = contactPhoneService.createContactPhone(workshop, data.contact().ddi(), data.contact().number());
        workshop.getContactPhones().add(contactPhone);
        WorkshopDetailsResponse response = workshopService.workshopToResponse(workshop);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<WorkshopDetailsResponse>> getAllWorkshops() {
        return ResponseEntity.ok(workshopService.getAllWorkshops());
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactResponse> addContactPhone(@RequestBody @Valid CreateContactPhone data,
                                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UndefinedAuthHeaderException("Identifique-se para acessar este recurso!");

        String login = tokenService.getSubject(authHeader.replace("Bearer ", ""));
        Workshop client = workshopService.getWorkshopByLogin(login);
        ContactPhone contactPhone = contactPhoneService.createContactPhone(client, data.ddi(), data.number());
        return ResponseEntity.status(201).body(new ContactResponse(contactPhone.getId(), contactPhone.formattedNumber()));
    }
}
