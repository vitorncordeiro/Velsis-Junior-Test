package com.example.versis.service;

import com.example.versis.dto.UserRequest;
import com.example.versis.mapper.UserMapper;
import com.example.versis.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;

    @Transactional
    public void createUser(UserRequest userRequest){
        userRepository.save(UserMapper.requestToModel(userRequest));
        mailService.sendMail(userRequest.email(), "Welcome!", "Hello " + userRequest.username() + ", welcome to our platform!");
    }

}
