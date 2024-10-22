package com.penaestrada.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.aspectj.weaver.ast.Not;
import org.hibernate.validator.constraints.URL;

public record CreateWorkshop(
        @NotNull
        String name,
        @NotNull
        String address,
        @NotNull
        String number,
        @NotNull
        @Pattern(regexp = "\\d{5}-\\d{3}")
        String zipCode,
        @NotNull
        String neighborhood,
        @NotNull
        String city,
        @NotNull
        String state,
        @NotNull
        Double rating,
        @NotNull
        @URL
        String mapsUrl,
        @NotNull
        Login login,
        @NotNull
        CreateContactPhone contact
) {
}
