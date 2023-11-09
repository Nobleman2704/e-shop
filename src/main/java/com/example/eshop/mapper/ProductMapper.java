package com.example.eshop.mapper;

import com.example.eshop.domain.entity.Product;
import com.example.eshop.dto.request.ProductRequest;
import com.example.eshop.dto.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductRequest, ProductResponse>{

}
