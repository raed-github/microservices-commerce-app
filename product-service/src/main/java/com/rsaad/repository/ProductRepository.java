package com.rsaad.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rsaad.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
