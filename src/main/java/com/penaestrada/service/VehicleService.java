package com.penaestrada.service;

import com.penaestrada.dto.CreateVehicle;
import com.penaestrada.model.Client;
import com.penaestrada.model.Vehicle;
import com.penaestrada.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    public Vehicle registerVehicle(CreateVehicle vehicle, Client client) {
        if (repository.existsByLicensePlate(vehicle.licensePlate().replace("-", ""))) {
            throw new IllegalArgumentException("Veículo já cadastrado com a placa informada");
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
}
