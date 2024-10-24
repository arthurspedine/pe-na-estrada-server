package com.penaestrada.dto;

import jakarta.validation.constraints.NotNull;

public record CreateEstimateRequest(
        @NotNull
        Long workshopId,
        @NotNull
        Long vehicleId,
        @NotNull
        String description,
        @NotNull
        String scheduledAt
) {
}
