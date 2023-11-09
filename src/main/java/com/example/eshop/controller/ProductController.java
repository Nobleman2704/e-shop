package com.example.eshop.controller;

import com.example.eshop.dto.request.ProductRequest;
import com.example.eshop.dto.response.ProductResponse;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.service.ProductService;
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
@RequestMapping("api/v1/product_crud")
@Tag(name = "PRODUCT", description = "product-crud")
public class ProductController {

    private final ProductService productService;

    @Operation(description = "Creating product")
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request,
            BindingResult bindingResult){
        if (bindingResult.hasErrors())
            throw new GlobalEShopException(ExceptionUtil.extractAllErrors(bindingResult));

        return ResponseEntity.ok(productService.create(request));
    }

    @Operation(description = "Filling product amount")
    @PatchMapping("/add_amount")
    public ResponseEntity<ProductResponse> addAmount(
            @Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.addAmount(request));
    }

    @Operation(description = "Get all products")
    @GetMapping("/get_all")
    public ResponseEntity<List<ProductResponse>> getAll(){
        List<ProductResponse> productServiceAll = productService.getAll();
        return ResponseEntity.ok(productServiceAll);
    }
}
