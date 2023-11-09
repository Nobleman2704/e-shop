package com.example.eshop.service.impl;

import com.example.eshop.domain.entity.Product;
import com.example.eshop.dto.request.ProductRequest;
import com.example.eshop.dto.response.ProductResponse;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.mapper.ProductMapper;
import com.example.eshop.repository.ProductRepository;
import com.example.eshop.service.ProductService;
import io.micrometer.core.instrument.util.StringUtils;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse create(ProductRequest request) {
        if (StringUtils.isBlank(request.getName()))
            throw new GlobalEShopException("Product name is required");

        if (request.getPrice()<=0)
            throw new GlobalEShopException("Product price must be positive");

        Product product = productMapper.toEntity(request);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductResponse addAmount(ProductRequest request) {
        int amount = request.getAmount();
        long productId = request.getId();
        Product product = findById(productId);
        product.setAmount(product.getAmount()+amount);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public Product findById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new GlobalEShopException(
                        String.format("Product not found by this id --> %s", productId)));
    }

    @Override
    public void saveAll(List<Product> productList) {
        productRepository.saveAll(productList);
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> all = productRepository.findAll();
        return productMapper.toDto(all);
    }
}
