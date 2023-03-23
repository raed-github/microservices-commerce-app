package com.rsaad.inventoryservice.service;

import com.rsaad.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    public List<InventoryResponse> isInStock(List<String> skuCode) throws InterruptedException;
}
