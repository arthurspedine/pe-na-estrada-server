package com.penaestrada.dto;

public record LoginResponse(
        String token,
        String email
) {
}
