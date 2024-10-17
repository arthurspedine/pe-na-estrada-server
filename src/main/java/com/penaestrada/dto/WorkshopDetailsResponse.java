package com.penaestrada.dto;

import java.util.List;

public record WorkshopDetailsResponse(
        Long id,
        String name,
        String address,
        String number,
        String zipCode,
        String neighborhood,
        String city,
        String state,
        Double rating,
        String mapsUrl,
        List<ContactResponse> contacts
) {
}
