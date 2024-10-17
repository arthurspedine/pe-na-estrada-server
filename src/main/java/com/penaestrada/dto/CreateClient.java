package com.penaestrada.dto;

public record CreateClient(
        String name,
        String cpf,
        String birthDate,
        Login login,
        CreateVehicle vehicle
) {
}
