package com.penaestrada.controller;

import com.penaestrada.dto.*;
import com.penaestrada.infra.security.TokenService;
import com.penaestrada.model.*;
import com.penaestrada.model.exception.UndefinedAuthHeaderException;
import com.penaestrada.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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
    @Autowired
    private WorkshopService workshopService;

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
        String login = tokenService.getSubject(token);
        ClientDetailsResponse response = clientService.getClientDashboard(login);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/contact")
    @Transactional
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
    @Transactional
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

    @GetMapping("/chatbot/init")
    public ResponseEntity<ChatbotInitRequest> chatbotInitRequest(@CookieValue(value = "pe_access_token") String token) {
        String login = tokenService.getSubject(token);
        Client client = clientService.getClientByLogin(login);
        List<SimpleResponse> vehicles = client.getVehicles().stream()
                .map(v -> new SimpleResponse(v.getId(), v.getBrand() + " " + v.getModel() + " " + v.getYear()))
                .toList();
        List<SimpleResponse> workshops = workshopService.findAllWorkshops().stream()
                .map(w -> new SimpleResponse(w.getId(), w.getName()))
                .toList();
        return ResponseEntity.ok().body(new ChatbotInitRequest(client.getName(), vehicles, workshops));
    }

    @PostMapping("/vehicle")
    @Transactional
    public ResponseEntity<VehicleResponse> createVehicle(@CookieValue(value = "pe_access_token") String token, @RequestBody @Valid CreateVehicle data) {
        String login = tokenService.getSubject(token);
        Client client = clientService.getClientByLogin(login);
        Vehicle vehicle = vehicleService.registerVehicle(data, client);
        return ResponseEntity.status(201).body(new VehicleResponse(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(),
                String.valueOf(vehicle.getYear()), vehicle.getLicensePlate()));
    }

    @DeleteMapping("/vehicle")
    @Transactional
    public ResponseEntity<Void> deleteVehicle(@CookieValue(value = "pe_access_token") String token, @RequestParam Long vehicleId) {
        String login = tokenService.getSubject(token);
        Client client = clientService.getClientByLogin(login);
        try {
            vehicleService.deleteVehicle(vehicleId, client.getVehicles());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
