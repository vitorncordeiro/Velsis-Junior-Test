package com.example.versis.mapper;

import com.example.versis.dto.request.UserRequest;
import com.example.versis.dto.response.UserResponse;
import com.example.versis.model.UserModel;

public class UserMapper {
    public static UserModel requestToModel(UserRequest userRequest){
        return new UserModel(
                userRequest.username(),
                userRequest.email(),
                userRequest.password()
                );
    }
    public static UserResponse modelToResponse(UserModel userModel){
        return new UserResponse(
                userModel.getUsername(),
                userModel.getEmail()
        );
    }
}
