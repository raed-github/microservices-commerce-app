package com.rsaad.orderservice.exceptions;

import lombok.AllArgsConstructor;

public class ErrorProcessingOrderException extends RuntimeException{
    private String msg;

    public ErrorProcessingOrderException(String msg){
        super(msg);
    }
}
