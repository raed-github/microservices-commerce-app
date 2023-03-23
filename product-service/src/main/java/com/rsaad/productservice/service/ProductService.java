package com.rsaad.productservice.service;

import java.util.List;

import com.rsaad.productservice.dto.ProductRequest;
import com.rsaad.productservice.dto.ProductResponse;

public interface ProductService {
	
	public void createProduct(ProductRequest productRequest);
	public List<ProductResponse> getAllProducts();
}
