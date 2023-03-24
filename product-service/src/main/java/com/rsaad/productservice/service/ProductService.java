package com.rsaad.productservice.service;

import java.util.List;

import com.rsaad.productservice.dto.ProductRequest;
import com.rsaad.productservice.dto.ProductResponse;

public interface ProductService {
	
	void createProduct(ProductRequest productRequest);
	List<ProductResponse> getAllProducts();
}
