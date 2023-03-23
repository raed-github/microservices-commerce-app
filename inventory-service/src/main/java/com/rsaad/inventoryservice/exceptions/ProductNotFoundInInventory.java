package com.rsaad.inventoryservice.exceptions;

public class ProductNotFoundInInventory extends RuntimeException{
    private String msg;

    public ProductNotFoundInInventory(String msg){
        super(msg);
    }
}
