package com.penaestrada.dto;

public record ClientCreateAddress(
        String street,
        String number,
        String complement,
        String zipCode,
        String neighborhood,
        String city,
        String state
) {
}
