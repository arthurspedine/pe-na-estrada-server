package com.penaestrada.dto;

import java.util.List;

public record ChatbotInitRequest(
        String username,
        List<SimpleResponse> vehicles,
        List<SimpleResponse> workshops
) {
}
