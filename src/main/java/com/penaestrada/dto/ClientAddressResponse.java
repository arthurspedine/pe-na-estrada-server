package com.penaestrada.dto;

public record ClientAddressResponse(
        Long id,
        String street,
        String number,
        String complement,
        String zipCode,
        String neighborhood,
        String city,
        String state
) {
}
