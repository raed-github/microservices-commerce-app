package com.rsaad.inventoryservice.service;

import com.rsaad.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    public Boolean isInStock(String skuCode);
    public List<InventoryResponse> findInventoriesBySkuCode(List<String> skuCodes);
    }
