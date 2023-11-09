package com.example.eshop.handler;

import com.example.eshop.exception.GlobalEShopException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalEShopException.class)
    public ResponseEntity<String> globalEShopException(GlobalEShopException globalEShopException){
        return ResponseEntity.badRequest().body(globalEShopException.getMessage());
    }
}
