package com.example.eshop.mapper;

import com.example.eshop.domain.entity.Order;
import com.example.eshop.dto.request.OrderRequest;
import com.example.eshop.dto.response.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderRequest, OrderResponse>{
}
