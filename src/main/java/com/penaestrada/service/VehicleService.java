package com.penaestrada.service;

import com.penaestrada.dto.CreateVehicle;
import com.penaestrada.model.Client;
import com.penaestrada.model.Vehicle;
import com.penaestrada.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    public Vehicle registerVehicle(CreateVehicle vehicle, Client client) {
        if (repository.existsByLicensePlate(vehicle.licensePlate().replace("-", ""))) {
            throw new IllegalArgumentException("Veículo já cadastrado com a placa informada.");
        }

        Vehicle newVehicle = new Vehicle();
        newVehicle.setBrand(vehicle.brand());
        newVehicle.setModel(vehicle.model());
        newVehicle.setYear(Integer.parseInt(vehicle.year()));
        newVehicle.setLicensePlate(vehicle.licensePlate());
        newVehicle.setClient(client);
        repository.save(newVehicle);
        return newVehicle;
    }

    public void deleteVehicle(Long vehicleId, List<Vehicle> vehicles) {
        if (!repository.existsById(vehicleId)) {
            throw new EntityNotFoundException("Veículo não existe.");
        }
        Optional<Vehicle> vehicle = vehicles.stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst();
        if (vehicle.isPresent()) {
            repository.deleteById(vehicleId);
        } else {
            throw new RuntimeException("Esse veículo não te pertence.");
        }
    }
}
