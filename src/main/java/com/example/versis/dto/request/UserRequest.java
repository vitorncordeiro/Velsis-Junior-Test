package com.example.versis.dto;

public record UserRequest(
        String username,
        String email,
        String password
) {
}
