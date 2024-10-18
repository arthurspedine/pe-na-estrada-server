package com.penaestrada.service;

import com.penaestrada.dto.ClientAddressResponse;
import com.penaestrada.dto.ClientCreateAddress;
import com.penaestrada.model.Client;
import com.penaestrada.model.ClientAddress;
import com.penaestrada.repository.ClientAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientAddressService {

    @Autowired
    private ClientAddressRepository repository;

    public ClientAddress createAddress(Client client, ClientCreateAddress data) {
        ClientAddress address = new ClientAddress();
        address.setClient(client);
        address.setStreet(data.street());
        address.setNumber(Integer.parseInt(data.number()));
        address.setComplement(data.complement());
        address.setZipCode(data.zipCode());
        address.setNeighborhood(data.neighborhood());
        address.setCity(data.city());
        address.setState(data.state());

        address.setClient(client);

        repository.save(address);
        return address;
    }

    public ClientAddressResponse addressToResponse(ClientAddress address) {
        return new ClientAddressResponse(
                address.getId(),
                address.getStreet(),
                String.valueOf(address.getNumber()),
                address.getComplement(),
                address.getZipCode(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState()
        );
    }

    public List<ClientAddress> listAddresses(Client client) {
        return repository.findByClient(client);
    }
}
