package com.example.versis.mapper;

import com.example.versis.dto.request.UserRequest;
import com.example.versis.dto.response.UserResponse;
import com.example.versis.model.UserModel;

public class UserMapper {
    public static UserModel requestToModel(UserRequest userRequest){
        return UserModel.builder()
                .email(userRequest.email())
                .username(userRequest.username())
                .password(userRequest.password())
                .build();
    }
    public static UserResponse modelToResponse(UserModel userModel){
        return new UserResponse(
                userModel.getUsername(),
                userModel.getEmail()
        );
    }
}
