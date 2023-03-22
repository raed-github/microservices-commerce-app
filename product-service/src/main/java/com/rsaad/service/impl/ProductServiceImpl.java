package com.rsaad.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rsaad.dto.ProductRequest;
import com.rsaad.dto.ProductResponse;
import com.rsaad.dto.mapper.DtoMapper;
import com.rsaad.model.Product;
import com.rsaad.repository.ProductRepository;
import com.rsaad.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

	public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(DtoMapper::mapToProductResponse).toList();
    }
}
