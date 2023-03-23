package com.rsaad.inventoryservice.service.impl;

import com.rsaad.inventoryservice.dto.InventoryResponse;
import com.rsaad.inventoryservice.exceptions.ProductNotFoundInInventory;
import com.rsaad.inventoryservice.model.Inventory;
import com.rsaad.inventoryservice.repository.InventoryRepository;
import com.rsaad.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

   public Boolean isInStock(String skuCode) {
       Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
               .orElseThrow(() -> new ProductNotFoundInInventory("Cannot find product by sku code " + skuCode));
       System.out.println(inventory.getQuantity());
       return inventory.getQuantity() > 0;
   }
}
