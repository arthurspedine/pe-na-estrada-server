package com.penaestrada.dto;

public record CreateVehicle(
        String brand,
        String model,
        String year,
        String licensePlate
) {
}
