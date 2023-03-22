package com.rsaad.dto.mapper;

import com.rsaad.dto.ProductResponse;
import com.rsaad.model.Product;

public class DtoMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
