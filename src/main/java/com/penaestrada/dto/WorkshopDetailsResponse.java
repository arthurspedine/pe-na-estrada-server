package com.penaestrada.dto;

import java.util.List;

public record WorkshopDetailsResponse(
        Long id,
        String name,
        WorkshopAddressResponse address,
        Double rating,
        String mapsUrl,
        List<ContactResponse> contacts
) {
}
