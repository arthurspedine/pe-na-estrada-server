package com.penaestrada.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateContactPhone(
        @NotNull
        @Pattern(regexp = "\\d{2}")
        String ddi,
        @NotNull
        @Pattern(regexp = "\\d{4,5}-\\d{4}")
        String number
) {
}
