package com.penaestrada.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record Login(
        @Email
        @NotNull
        String email,
        @NotNull
        String password
) {
}
