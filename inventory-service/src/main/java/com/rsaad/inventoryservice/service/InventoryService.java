package com.rsaad.inventoryservice.service;

import com.rsaad.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    Boolean isInStock(String skuCode);
}
