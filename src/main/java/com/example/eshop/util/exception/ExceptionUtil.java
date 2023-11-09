package com.example.eshop.util.exception;

import org.springframework.validation.BindingResult;

public class ExceptionUtil {
    public static String extractAllErrors(BindingResult bindingResult){
        StringBuilder result = new StringBuilder();
        bindingResult.getAllErrors()
                .forEach(error -> result.append(error.getDefaultMessage()).append("\n"));
        return result.toString();
    }
}
