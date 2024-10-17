package com.penaestrada.repository;

import com.penaestrada.model.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long> {
}
