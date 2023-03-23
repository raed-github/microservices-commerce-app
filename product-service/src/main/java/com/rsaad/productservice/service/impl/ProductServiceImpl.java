package com.rsaad.productservice.service.impl;

import java.util.List;

import com.rsaad.productservice.dto.ProductRequest;
import com.rsaad.productservice.dto.ProductResponse;
import com.rsaad.productservice.dto.mapper.DtoMapper;
import com.rsaad.productservice.model.Product;
import com.rsaad.productservice.repository.ProductRepository;
import com.rsaad.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

	public void createProduct(ProductRequest productRequest) {
        Product product = DtoMapper.mapToProduct(productRequest);
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(DtoMapper::mapToProductResponse).toList();
    }
}
