package com.example.eshop.mapper;

import com.example.eshop.domain.entity.User;
import com.example.eshop.dto.request.UserRequest;
import com.example.eshop.dto.response.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserRequest, UserResponse>{
}
