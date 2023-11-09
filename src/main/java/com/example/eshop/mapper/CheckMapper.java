package com.example.eshop.mapper;

import com.example.eshop.domain.entity.Check;
import com.example.eshop.dto.request.CheckRequest;
import com.example.eshop.dto.response.CheckResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CheckMapper extends BaseMapper<Check, CheckRequest, CheckResponse>{
}
