package com.rsaad.orderservice.service.impl;

import com.rsaad.orderservice.client.InventoryClient;
import com.rsaad.orderservice.constants.OrderServiceContants;
import com.rsaad.orderservice.dto.OrderRequest;
import com.rsaad.orderservice.dto.mapper.DtoMapper;
import com.rsaad.orderservice.exceptions.ErrorProcessingOrderException;
import com.rsaad.orderservice.model.Order;
import com.rsaad.orderservice.repository.OrderRepository;
import com.rsaad.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String placeOrder(OrderRequest orderRequest) {
             Order order = DtoMapper.maptToOrder(orderRequest);
//        TraceCircuitBreaker traceCircuitBreaker = circuitBreakerFactory.create("inventory");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
        Supplier<Boolean> booleanSupplier = () -> order.getOrderLineItemsList().stream()
                .allMatch(orderLineItems -> inventoryClient.checkStock(orderLineItems.getSkuCode()));
        //is going directly to handleErrorCase() regardless the value of booleanSupplier
        //        boolean productsInStock = circuitBreaker.run(booleanSupplier,throwable -> handleErrorCase(throwable));
        inventoryClient.checkStock("IPONE_10_BLUE");
//        boolean productsInStock = circuitBreaker.run(booleanSupplier);
        boolean productsInStock = false;
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
