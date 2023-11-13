package com.example.eshop.controller;

import com.example.eshop.annotation.logging.Loggable;
import com.example.eshop.dto.request.UserRequest;
import com.example.eshop.dto.response.UserResponse;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.service.UserService;
import com.example.eshop.util.exception.ExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user_crud")
@Tag(name = "USER", description = "user-crud")
public class UserController {

    private final UserService userService;

    @Loggable
    @Operation(description = "Create User, If username exists in the database, it throw exception")
    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(
           @Valid @RequestBody UserRequest userRequest,
            BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new GlobalEShopException(ExceptionUtil.extractAllErrors(bindingResult));

        return ResponseEntity.ok(userService.create(userRequest));
    }
}
