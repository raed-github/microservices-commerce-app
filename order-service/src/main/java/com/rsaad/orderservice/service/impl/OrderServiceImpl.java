package com.rsaad.orderservice.service.impl;

import com.rsaad.orderservice.client.InventoryClient;
import com.rsaad.orderservice.constants.OrderServiceContants;
import com.rsaad.orderservice.dto.InventoryResponse;
import com.rsaad.orderservice.dto.OrderRequest;
import com.rsaad.orderservice.dto.mapper.DtoMapper;
import com.rsaad.orderservice.exceptions.OrderNotFoundException;
import com.rsaad.orderservice.model.Order;
import com.rsaad.orderservice.model.OrderLineItems;
import com.rsaad.orderservice.repository.OrderRepository;
import com.rsaad.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private InventoryClient inventoryClient;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(DtoMapper::mapToOrderLineItemsDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Call Inventory Service, and place order if product is in
        // stock
        boolean allProductsInStock = inventoryClient.checkStock(skuCodes)
                .stream()
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            return OrderServiceContants.ORDER_PLACED_SUCCESSFULLY;
        } else {
            throw new OrderNotFoundException(OrderServiceContants.PRODUCT_NOT_FOUND);
        }
    }
}
