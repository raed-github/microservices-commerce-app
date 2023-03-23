package com.rsaad.productservice.exceptions;

public class ProductNotFoundException extends RuntimeException{
    private String msg;
    public ProductNotFoundException(String msg){
        super(msg);
    }
}
