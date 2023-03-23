package com.rsaad.productservice.dto.mapper;

import com.rsaad.productservice.dto.ProductRequest;
import com.rsaad.productservice.dto.ProductResponse;
import com.rsaad.productservice.model.Product;

public class DtoMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public static Product mapToProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }
}
