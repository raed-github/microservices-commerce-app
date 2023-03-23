package com.rsaad.orderservice.exceptions;

public class OrderNotFoundException extends RuntimeException{
    private String msg;
    public OrderNotFoundException(String msg){
        super(msg);
    }
}
