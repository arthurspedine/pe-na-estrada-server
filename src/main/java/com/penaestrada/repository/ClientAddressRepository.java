package com.penaestrada.repository;

import com.penaestrada.model.Client;
import com.penaestrada.model.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long> {
    List<ClientAddress> findByClient(Client client);
}
