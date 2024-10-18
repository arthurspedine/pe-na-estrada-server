package com.penaestrada.dto;

public record WorkshopAddressResponse(
        String address,
        String number,
        String zipCode,
        String neighborhood,
        String city,
        String state,
        Double rating,
        String mapsUrl
) {
}
