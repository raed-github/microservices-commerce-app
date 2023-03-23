package com.rsaad.orderservice.service.impl;

import com.rsaad.orderservice.constants.OrderServiceContants;
import com.rsaad.orderservice.dto.InventoryResponse;
import com.rsaad.orderservice.dto.OrderLineItemsDto;
import com.rsaad.orderservice.dto.OrderRequest;
import com.rsaad.orderservice.dto.mapper.DtoMapper;
import com.rsaad.orderservice.exceptions.OrderNotFoundException;
import com.rsaad.orderservice.model.Order;
import com.rsaad.orderservice.model.OrderLineItems;
import com.rsaad.orderservice.repository.OrderRepository;
import com.rsaad.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
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
        InventoryResponse[] inventoryResponsArray = webClient.get()
                .uri("http://localhost:8083/api/inventories",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        } else {
            throw new OrderNotFoundException(OrderServiceContants.PRODUCT_NOT_FOUND);
        }
    }
}
