package com.example.versis.service;

import com.example.versis.dto.request.UserFilter;
import com.example.versis.dto.request.UserRequest;
import com.example.versis.dto.response.UserResponse;
import com.example.versis.exception.BusinessException;
import com.example.versis.mapper.UserMapper;
import com.example.versis.model.UserModel;
import com.example.versis.repository.UserRepository;
import com.example.versis.specification.UserSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserRequest userRequest){
        UserModel userModel = UserMapper.requestToModel(userRequest);
        userModel.setPassword(passwordEncoder.encode(userRequest.password()));
        validateUser(userRequest);
        userRepository.save(userModel);
        mailService.sendMail(userRequest.email(), "Welcome!", "Hello "
                + userRequest.username() + ", welcome to our platform!");
    }
    public Page<UserResponse> getUsers(Pageable pageable, UserFilter filter){
        return userRepository.findAll(UserSpecification.withFilters(filter), pageable)
                .map(UserMapper::modelToResponse);
    }

    private void validateUser(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email already exists");
        }

        List<String> emptyFields = new ArrayList<>();
        if (request.email().isEmpty()) emptyFields.add("email");
        if (request.username().isEmpty()) emptyFields.add("username");
        if (request.password().isEmpty()) emptyFields.add("password");

        if (!emptyFields.isEmpty()) {
            throw new BusinessException("The following fields cannot be empty: " +
                    String.join(", ", emptyFields));
        }
    }
}
