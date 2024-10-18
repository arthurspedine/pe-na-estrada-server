package com.penaestrada.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateVehicle(
        @NotNull
        String brand,
        @NotNull
        String model,
        @NotNull
        @Pattern(regexp = "\\d{4}")
        String year,
        @NotNull
        @Pattern(regexp = "\\d{4}-\\d{3}")
        String licensePlate
) {
}
