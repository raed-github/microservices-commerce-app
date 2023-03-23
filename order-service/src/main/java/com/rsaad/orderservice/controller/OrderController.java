package com.rsaad.orderservice.controller;

import com.rsaad.orderservice.constants.OrderServiceContants;
import com.rsaad.orderservice.dto.OrderRequest;
import com.rsaad.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return OrderServiceContants.ORDER_PLACED_MESSAGE;
    }
}
