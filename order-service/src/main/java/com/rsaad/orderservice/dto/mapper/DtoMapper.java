package com.rsaad.orderservice.dto.mapper;

import com.rsaad.orderservice.dto.OrderLineItemsDto;
import com.rsaad.orderservice.dto.OrderRequest;
import com.rsaad.orderservice.model.Order;
import com.rsaad.orderservice.model.OrderLineItems;

public class DtoMapper {

    public static OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

    public static Order maptToOrder(OrderRequest orderRequest){
       return Order.builder()
                .orderLineItemsList(orderRequest.getOrderLineItemsDtoList()
                        .stream()
                        .map(DtoMapper::mapToOrderLineItems).toList())
                .build();
    }
}
