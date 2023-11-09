package com.example.eshop.service;


import com.example.eshop.domain.entity.User;
import com.example.eshop.dto.request.UserRequest;
import com.example.eshop.dto.response.UserResponse;

public interface UserService {
    User findById(long userId);

    UserResponse create(UserRequest userRequest);
}
