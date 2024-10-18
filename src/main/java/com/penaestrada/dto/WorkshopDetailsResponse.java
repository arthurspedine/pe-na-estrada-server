package com.penaestrada.dto;

import java.util.List;

public record WorkshopDetailsResponse(
        Long id,
        String name,
        WorkshopAddressResponse address,
        List<ContactResponse> contacts
) {
}
