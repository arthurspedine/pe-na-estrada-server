package com.penaestrada.dto;

public record VehicleResponse(
        Long id,
        String brand,
        String model,
        String year,
        String licensePlate
) {
}
