package com.example.eshop.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;


@Data
public class UserRequest {
    @Pattern(regexp = "^[a-zA-Z]{4,20}$", message = "Username length should be this range [4,20], only letters")
    private String name;
}
