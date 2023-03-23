package com.rsaad.orderservice.service;

import com.rsaad.orderservice.dto.OrderRequest;

public interface OrderService {
    public String placeOrder(OrderRequest orderRequest);
}
