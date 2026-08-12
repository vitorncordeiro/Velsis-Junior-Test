package com.example.velsis.service;

import com.example.velsis.dto.request.UserRequest;
import com.example.velsis.dto.util.MailDto;
import com.example.velsis.exception.BusinessException;
import com.example.velsis.model.UserModel;
import com.example.velsis.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService - Create User Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailService mailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequest validUserRequest;
    private UserModel userModel;

    @BeforeEach
    void setUp() {
        validUserRequest = new UserRequest(
                "test",
                "test@example.com",
                "password123"
        );

        userModel = new UserModel();
        userModel.setEmail(validUserRequest.email());
        userModel.setUsername(validUserRequest.username());
    }

    @Test
    @DisplayName("Should create user successfully when data is valid")
    void shouldCreateUserSuccessfully() {
        String encodedPassword = "encoded_password_hash";
        when(userRepository.existsByEmail(validUserRequest.email())).thenReturn(false);
        when(passwordEncoder.encode(validUserRequest.password())).thenReturn(encodedPassword);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        userService.createUser(validUserRequest);

        verify(passwordEncoder, times(1)).encode(validUserRequest.password());

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        assertEquals(encodedPassword, userCaptor.getValue().getPassword());

        verify(mailService, times(1)).sendMail(
                new MailDto(
                        validUserRequest.email(),
                        "Welcome!",
                        "Hello " + validUserRequest.username() + ", welcome to our platform!"
                )
        );
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(validUserRequest.email())).thenReturn(true);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> userService.createUser(validUserRequest)
        );

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository, never()).save(any());
        verify(mailService, never()).sendMail(
                new MailDto(
                        validUserRequest.email(),
                        "Welcome!",
                        "Hello " + validUserRequest.username() + ", welcome to our platform!"
                )
        );
    }

    @Test
    @DisplayName("Should throw exception when email is empty")
    void shouldThrowExceptionWhenEmailIsEmpty() {
        UserRequest invalidRequest = new UserRequest(
                "test",
                "",
                "password123"
        );

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> userService.createUser(invalidRequest)
        );

        assertTrue(exception.getMessage().contains("email"));
        verify(userRepository, never()).save(any());
        verify(mailService, never()).sendMail(
                new MailDto(
                        invalidRequest.email(),
                        "Welcome!",
                        "Hello " + invalidRequest.username() + ", welcome to our platform!"
                )
        );
    }

    @Test
    @DisplayName("Should throws exception when multiple fields are empty")
    void shouldThrowExceptionWhenMultipleFieldsAreEmpty() {
        UserRequest invalidRequest = new UserRequest(
                "",
                "",
                ""
        );

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> userService.createUser(invalidRequest)
        );

        String message = exception.getMessage();
        assertTrue(message.contains("email"));
        assertTrue(message.contains("username"));
        assertTrue(message.contains("password"));
        verify(userRepository, never()).save(any());
    }
    @Test
    @DisplayName("Should throw exception when password is empty")
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        UserRequest invalidRequest = new UserRequest(
                "test",
                "test@example.com",
                ""
        );

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> userService.createUser(invalidRequest)
        );

        assertTrue(exception.getMessage().contains("password"));
        verify(userRepository, never()).save(any());
        verify(mailService, never()).sendMail(
                new MailDto(
                        invalidRequest.email(),
                        "Welcome!",
                        "Hello " + invalidRequest.username() + ", welcome to our platform!"
                )
        );
    }
    @Test
    @DisplayName("Should throw exception when username is empty")
    void shouldThrowExceptionWhenUsernameIsEmpty() {
        UserRequest invalidRequest = new UserRequest(
                "",
                "test@example.com",
                "123"
        );

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> userService.createUser(invalidRequest)
        );

        assertTrue(exception.getMessage().contains("username"));
        verify(userRepository, never()).save(any());
        verify(mailService, never()).sendMail(
                new MailDto(
                        invalidRequest.email(),
                        "Welcome!",
                        "Hello " + invalidRequest.username() + ", welcome to our platform!"
                )
        );
    }

}
