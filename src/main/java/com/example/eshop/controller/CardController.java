package com.example.eshop.controller;

import com.example.eshop.dto.request.PaymentCardRequest;
import com.example.eshop.dto.response.PaymentCardResponse;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.service.CardService;
import com.example.eshop.util.exception.ExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/card-crud")
@Tag(name = "PAYMENT_CARD", description = "payment_card-crud")
public class CardController {
    private final CardService cardService;

    @Operation(description = "Only uz_card, visa, master_card, humo card types are acceptable!")
    @PostMapping("/create")
    public ResponseEntity<PaymentCardResponse> create(
            @Valid @RequestBody PaymentCardRequest paymentCardRequest,
            BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new GlobalEShopException(ExceptionUtil.extractAllErrors(bindingResult));

        return ResponseEntity.ok(cardService.create(paymentCardRequest));
    }

    @Operation(description = "Filling balance")
    @PutMapping("/fill_balance")
    public ResponseEntity<PaymentCardResponse> fillBalance(
            @RequestParam long cardId,
            @RequestParam double amount){
        return ResponseEntity.ok(cardService.fillBalance(cardId, amount));
    }
}
