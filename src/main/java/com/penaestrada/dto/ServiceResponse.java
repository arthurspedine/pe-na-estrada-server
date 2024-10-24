package com.penaestrada.dto;

public record ServiceResponse(
        Long id,
        String description,
        Double price,
        String createdAt
) {
}
