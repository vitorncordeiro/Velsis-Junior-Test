package com.example.velsis.mapper;

import com.example.velsis.dto.request.UserRequest;
import com.example.velsis.dto.response.UserResponse;
import com.example.velsis.model.UserModel;

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
