package com.penaestrada.controller;

import com.penaestrada.dto.*;
import com.penaestrada.infra.security.TokenService;
import com.penaestrada.model.*;
import com.penaestrada.model.exception.UndefinedAuthHeaderException;
import com.penaestrada.service.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

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

    @Autowired
    private ClientAddressService clientAddressService;

    @PostMapping("/signup")
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

    @GetMapping("/dashboard")
    public ResponseEntity<ClientDetailsResponse> clientDashboard(@CookieValue(value = "pe_access_token") String token) {
        ClientDetailsResponse response = clientService.getClientDashboard(token);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactResponse> addContactPhone(@RequestBody @Valid CreateContactPhone data,
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UndefinedAuthHeaderException("Identifique-se para acessar este recurso!");

        String login = tokenService.getSubject(authHeader.replace("Bearer ", ""));
        Client client = clientService.getClientByLogin(login);
        ContactPhone contactPhone = contactPhoneService.createContactPhone(client, data.ddi(), data.number());
        return ResponseEntity.status(201).body(new ContactResponse(contactPhone.getId(), contactPhone.formattedNumber()));
    }

    @PostMapping("/address")
    public ResponseEntity<ClientAddressResponse> addAddress(@RequestBody @Valid ClientCreateAddress data,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UndefinedAuthHeaderException("Identifique-se para acessar este recurso!");

        String login = tokenService.getSubject(authHeader.replace("Bearer ", ""));
        Client client = clientService.getClientByLogin(login);
        ClientAddress address = clientAddressService.createAddress(client, data);

        return ResponseEntity.status(201).body(clientAddressService.addressToResponse(address));
    }

    @GetMapping("/address")
    public ResponseEntity<List<ClientAddressResponse>> listAddresses(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UndefinedAuthHeaderException("Identifique-se para acessar este recurso!");

        String login = tokenService.getSubject(authHeader.replace("Bearer ", ""));
        Client client = clientService.getClientByLogin(login);
        List<ClientAddress> addresses = clientAddressService.listAddresses(client);
        List<ClientAddressResponse> response = new ArrayList<>();
        addresses.forEach(address -> response.add(clientAddressService.addressToResponse(address)));

        return ResponseEntity.ok().body(response);
    }

}
