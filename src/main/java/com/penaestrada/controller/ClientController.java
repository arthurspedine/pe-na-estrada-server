package com.penaestrada.controller;

import com.penaestrada.dto.*;
import com.penaestrada.infra.security.TokenService;
import com.penaestrada.model.Client;
import com.penaestrada.model.ContactPhone;
import com.penaestrada.model.User;
import com.penaestrada.model.Vehicle;
import com.penaestrada.service.ClientService;
import com.penaestrada.service.ContactPhoneService;
import com.penaestrada.service.UserService;
import com.penaestrada.service.VehicleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ContactPhoneService contactPhoneService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<ClientDetailsResponse> registerClient(@RequestBody @Valid CreateClient data) {
        User user = userService.createUser(data.login());
        Client client = clientService.registerClient(data.name(), data.cpf(), data.birthDate(), user);
        Vehicle vehicle = vehicleService.registerVehicle(data.vehicle(), client);
        List<VehicleResponse> vehicles = new ArrayList<>();
        vehicles.add(new VehicleResponse(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(),
                String.valueOf(vehicle.getYear()), vehicle.getLicensePlate()));

        ClientDetailsResponse response = new ClientDetailsResponse(
                client.getId(), user.getLogin(), client.getName(),
                client.getCpf(), client.getBirthDate().toString()
                , vehicles, new ArrayList<>()
        );
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid Login data) {
        var user = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = manager.authenticate(user);
        String token = tokenService.genToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token, data.email()));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ClientDetailsResponse> clientDashboard(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String login = tokenService.getSubject(authHeader.replace("Bearer ", ""));
        ClientDetailsResponse response = clientService.getClientDashboard(login);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactResponse> addContactPhone(@RequestBody @Valid CreateContactPhone data,
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String login = tokenService.getSubject(authHeader.replace("Bearer ", ""));
        Client client = clientService.getClientByLogin(login);
        ContactPhone contactPhone = contactPhoneService.createContactPhone(client, data.ddi(), data.number());
        return ResponseEntity.status(201).body(new ContactResponse(contactPhone.getId(), contactPhone.formattedNumber()));
    }

}