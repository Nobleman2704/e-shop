package com.example.eshop.service;

import com.example.eshop.domain.entity.Check;
import com.example.eshop.dto.request.CheckRequest;
import com.example.eshop.dto.response.CheckResponse;

public interface CheckService {

    CheckResponse create(Check check);

    String payment(CheckRequest checkRequest);
}
