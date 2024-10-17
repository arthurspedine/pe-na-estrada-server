package com.penaestrada.dto;

public record CreateWorkshop(
        String name,
        String address,
        String number,
        String zipCode,
        String neighborhood,
        String city,
        String state,
        Double rating,
        String mapsUrl,
        Login login
) {
}
