package com.penaestrada.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClientCreateAddress(
        @NotNull
        String street,
        @NotNull
        String number,
        @NotNull
        String complement,
        @NotNull
        @Pattern(regexp = "\\d{5}-\\d{3}")
        String zipCode,
        @NotNull
        String neighborhood,
        @NotNull
        String city,
        @NotNull
        String state
) {
}
