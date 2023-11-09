package com.example.eshop.service.impl;

import com.example.eshop.dto.request.UserRequest;
import com.example.eshop.dto.response.UserResponse;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.domain.entity.User;
import com.example.eshop.mapper.UserMapper;
import com.example.eshop.repository.UserRepository;
import com.example.eshop.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GlobalEShopException(String.format("User not found by this id: %s", userId)));
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        if (userRepository.existsByName(userRequest.getName()))
            throw new GlobalEShopException(String.format(
                    "Username already exists --> %s", userRequest.getName()));


        return userMapper.toDto(userRepository.save(userMapper.toEntity(userRequest)));
    }
}
