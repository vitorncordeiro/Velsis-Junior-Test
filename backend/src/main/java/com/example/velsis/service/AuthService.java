package com.example.velsis.service;

import com.example.velsis.dto.request.LoginRequest;
import com.example.velsis.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${spring.security.user.password}")
    private String adminPassword;

    public LoginResponse login(LoginRequest loginRequest) {
        if ("admin".equals(loginRequest.username()) && loginRequest.password().equals(adminPassword)) {
            String token = generateToken(loginRequest.username());
            return new LoginResponse(token);
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
    public String generateToken(String username){
        return "123";
    }
}
