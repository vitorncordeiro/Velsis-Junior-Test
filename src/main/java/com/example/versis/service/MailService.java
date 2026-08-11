package com.example.versis.service;

import org.springframework.stereotype.Service;

@Service
public class MailService {
    public void sendMail(String to, String subject, String body) {
        String email = """
                To: %s
                Subject: %s
                Body: %s""".formatted(to, subject, body);
        System.out.println(email);
    }
}
