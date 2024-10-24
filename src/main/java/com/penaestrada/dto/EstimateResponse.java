package com.penaestrada.dto;

import java.util.List;

public record EstimateResponse(
        Long id,
        String owner,
        VehicleResponse vehicle,
        WorkshopDetailsResponse workshop,
        String initialDescription,
        String scheduledAt,
        String createdAt,
        Double value,
        String finishedAt,
        List<ServiceResponse> services
) {
}
