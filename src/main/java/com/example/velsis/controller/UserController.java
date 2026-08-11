package com.example.versis.controller;

import com.example.versis.dto.request.UserFilter;
import com.example.versis.dto.request.UserRequest;
import com.example.versis.dto.response.UserResponse;
import com.example.versis.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
        return ResponseEntity.ok("User " + userRequest.username() + " created successfully");
    }
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers(Pageable pageable, UserFilter filter){
        return ResponseEntity.ok(userService.getUsers(pageable, filter));
    }
}
