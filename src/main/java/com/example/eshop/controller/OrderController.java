package com.example.eshop.controller;

import com.example.eshop.dto.request.OrderRequest;
import com.example.eshop.dto.request.ProductRequest;
import com.example.eshop.dto.response.CheckResponse;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.service.OrderService;
import com.example.eshop.service.impl.OrderServiceImpl;
import com.example.eshop.util.exception.ExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order_crud")
@Tag(name = "ORDER", description = "order-crud")
public class OrderController {
    private final OrderService orderService;

    @Operation(description = "Ordering product")
    @PostMapping("/make-order")
    public ResponseEntity<CheckResponse> orderProduct(
            @RequestParam("userId") long userId,
            @RequestBody() OrderRequest orderRequest,
            BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new GlobalEShopException(ExceptionUtil.extractAllErrors(bindingResult));
        return ResponseEntity.ok(orderService.orderProduct(userId, orderRequest));
    }

    @PutMapping("/cancel-order")
    @Operation(description = "Cancelling order")
    public ResponseEntity<String> cancelOrder(
            @RequestParam long userId,
            @RequestParam long orderId){
        return ResponseEntity.ok(orderService.cancelOrder(userId, orderId));
    }
}
