package com.rsaad.inventoryservice.controller;

import com.rsaad.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public Boolean isInStock(@PathVariable String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
