package com.example.versis.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull(message = "Username is required")
        String username,
        @NotNull(message = "Email is required")
        String email,
        @NotNull(message = "Password is required")
        String password
) {
}
