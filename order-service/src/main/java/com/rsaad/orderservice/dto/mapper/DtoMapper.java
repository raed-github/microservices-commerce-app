package com.rsaad.orderservice.dto.mapper;

import com.rsaad.orderservice.dto.OrderLineItemsDto;
import com.rsaad.orderservice.model.OrderLineItems;

public class DtoMapper {

    public static OrderLineItems mapToOrderLineItemsDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
