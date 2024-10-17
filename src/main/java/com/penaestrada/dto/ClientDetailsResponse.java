package com.penaestrada.dto;

import java.util.List;

public record ClientDetailsResponse(
        Long id,
        String email,
        String name,
        String cpf,
        String birthDate,
        List<VehicleResponse> vehicles,
        List<ContactResponse> contacts


) {
}
