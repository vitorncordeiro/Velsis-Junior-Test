package com.example.velsis.dto.util;

public record MailDto(
        String to,
        String subject,
        String body
) {
}
