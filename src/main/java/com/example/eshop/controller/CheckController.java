package com.example.eshop.controller;

import com.example.eshop.dto.request.CheckRequest;
import com.example.eshop.service.CheckService;
import com.example.eshop.service.impl.CheckServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/check-crud")
@Tag(name = "CHECK", description = "check-crud")
public class CheckController {
    private final CheckService checkService;

    @PostMapping("/payment")
    @Operation(description = "Payment operation")
    public ResponseEntity<String> payment(
            @RequestBody CheckRequest checkRequest){
        return ResponseEntity.ok(checkService.payment(checkRequest));
    }
}
