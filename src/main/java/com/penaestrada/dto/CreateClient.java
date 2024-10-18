package com.penaestrada.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateClient(
        @NotNull
        String name,
        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf,
        @NotNull
        String birthDate,
        @NotNull
        Login login,
        @NotNull
        CreateVehicle vehicle
) {
}
