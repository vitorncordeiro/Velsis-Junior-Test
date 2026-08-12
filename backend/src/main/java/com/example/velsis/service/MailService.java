package com.example.velsis.service;

import com.example.velsis.dto.util.MailDto;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    public void sendMail(MailDto mailDto) {
        String email = """
                To: %s
                Subject: %s
                Body: %s""".formatted(mailDto.to(), mailDto.subject(), mailDto.body());
        System.out.println(email);
    }
}
