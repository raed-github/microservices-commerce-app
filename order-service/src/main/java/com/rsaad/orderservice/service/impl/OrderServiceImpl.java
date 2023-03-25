package com.rsaad.orderservice.service.impl;

import com.rsaad.orderservice.client.InventoryClient;
import com.rsaad.orderservice.constants.OrderServiceContants;
import com.rsaad.orderservice.dto.InventoryResponse;
import com.rsaad.orderservice.dto.OrderRequest;
import com.rsaad.orderservice.dto.mapper.DtoMapper;
import com.rsaad.orderservice.event.kafka.PlaceOrderEvent;
import com.rsaad.orderservice.exceptions.ErrorProcessingOrderException;
import com.rsaad.orderservice.exceptions.OrderNotFoundException;
import com.rsaad.orderservice.model.Order;
import com.rsaad.orderservice.repository.OrderRepository;
import com.rsaad.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    private Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    @Autowired
    private InventoryClient inventoryClient;
    @Autowired
    private StreamBridge streamBridge;

    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, PlaceOrderEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = DtoMapper.maptToOrder(orderRequest);
        order.setOrderNumber(UUID.randomUUID().toString());
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(orderLineItems -> orderLineItems.getSkuCode()).toList();

        // Call Inventory Service, and place order if product is in
        // stock
        InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventories",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            if (allProductsInStock) {
                orderRepository.save(order);
                kafkaTemplate.send("notificationTopic", new PlaceOrderEvent(order.getOrderNumber()));
                return OrderServiceContants.ORDER_PLACED_SUCCESSFULLY;
            } else {
                throw new OrderNotFoundException(OrderServiceContants.PRODUCT_NOT_FOUND);
            }
        }
        return null;
    }
    public String placeOrder_KafkaVersion(OrderRequest orderRequest) {
        Order order = DtoMapper.maptToOrder(orderRequest);
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
        Supplier<Boolean> booleanSupplier = () -> order.getOrderLineItemsList().stream()
                .allMatch(orderLineItems -> inventoryClient.checkStock(orderLineItems.getSkuCode()));
        //is going directly to handleErrorCase() regardless the value of booleanSupplier
        //        boolean productsInStock = circuitBreaker.run(booleanSupplier,throwable -> handleErrorCase(throwable));
        boolean productsInStock = circuitBreaker.run(booleanSupplier);
        if (productsInStock) {
            order.setOrderNumber(UUID.randomUUID().toString());
            orderRepository.save(order);
            log.info("Sending order details to notification service");
            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(order.getId()).build());
            return OrderServiceContants.ORDER_PLACED_SUCCESSFULLY;
        } else {
            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(1234).build());
            throw new ErrorProcessingOrderException(OrderServiceContants.UNABLE_TO_PLACE_ORDER);
        }
    }

    public String placeOrder_rabbitMQVersion(OrderRequest orderRequest) {
             Order order = DtoMapper.maptToOrder(orderRequest);
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
        Supplier<Boolean> booleanSupplier = () -> order.getOrderLineItemsList().stream()
                .allMatch(orderLineItems -> inventoryClient.checkStock(orderLineItems.getSkuCode()));
        //is going directly to handleErrorCase() regardless the value of booleanSupplier
        //        boolean productsInStock = circuitBreaker.run(booleanSupplier,throwable -> handleErrorCase(throwable));
        boolean productsInStock = circuitBreaker.run(booleanSupplier);
        if (productsInStock) {
            order.setOrderNumber(UUID.randomUUID().toString());
            orderRepository.save(order);
            log.info("Sending order details to notification service");
            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(order.getId()).build());
            return OrderServiceContants.ORDER_PLACED_SUCCESSFULLY;
        } else {
            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(1234).build());
            throw new ErrorProcessingOrderException(OrderServiceContants.UNABLE_TO_PLACE_ORDER);
        }
    }
    private Boolean handleErrorCase(Throwable throwable) {
        System.out.print("error");
        System.out.println(throwable.getCause());
        System.out.println(throwable.getMessage());
        throwable.printStackTrace();
        return false;
    }
}
