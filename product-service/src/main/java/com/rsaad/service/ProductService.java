package com.rsaad.service;

import java.util.List;

import com.rsaad.dto.ProductRequest;
import com.rsaad.dto.ProductResponse;

public interface ProductService {
	
	public void createProduct(ProductRequest productRequest);
	public List<ProductResponse> getAllProducts();
}
