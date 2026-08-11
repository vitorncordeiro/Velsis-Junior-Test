package com.example.versis.dto.request;

public record UserRequest(
        String username,
        String email,
        String password
) {
}
