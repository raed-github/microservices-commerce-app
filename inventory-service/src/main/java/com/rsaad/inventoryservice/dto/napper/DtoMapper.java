package com.rsaad.inventoryservice.dto.napper;

import com.rsaad.inventoryservice.dto.InventoryResponse;
import com.rsaad.inventoryservice.model.Inventory;

public class DtoMapper {
    public static InventoryResponse mapToInventoryDto(Inventory inventory){
        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build();
    }
}
