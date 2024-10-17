package com.penaestrada.service;

import com.penaestrada.dto.ClientDetailsResponse;
import com.penaestrada.dto.ContactResponse;
import com.penaestrada.dto.VehicleResponse;
import com.penaestrada.model.Client;
import com.penaestrada.model.ContactPhone;
import com.penaestrada.model.User;
import com.penaestrada.model.Vehicle;
import com.penaestrada.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Client registerClient(String name, String cpf, String birthDate, User user) {
        Client newClient = new Client();
        newClient.setName(name);
        newClient.setCpf(cpf);
        newClient.setBirthDate(LocalDate.parse(birthDate));
        newClient.setUser(user);
        repository.save(newClient);
        return newClient;
    }

    public Client getClientByLogin(String login) {
        return repository.findByUserLogin(login).orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    public ClientDetailsResponse getClientDashboard(String login) {
        Client client = getClientByLogin(login);

        List<VehicleResponse> vehicles = new ArrayList<>();
        List<ContactResponse> contacts = new ArrayList<>();

        for (Vehicle vehicle : client.getVehicles()) {
            vehicles.add(new VehicleResponse(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(),
                    String.valueOf(vehicle.getYear()), vehicle.getLicensePlate()));
        }

        for (ContactPhone contact : client.getContactPhones()) {
            contacts.add(new ContactResponse(contact.getId(), contact.formattedNumber()));
        }

        return new ClientDetailsResponse(
                client.getId(), client.getUser().getLogin(), client.getName(),
                client.getCpf(), client.getBirthDate().toString(),
                vehicles, contacts
        );
    }


}
