package com.rsaad.inventoryservice.controller;

import com.rsaad.inventoryservice.dto.InventoryResponse;
import com.rsaad.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/is-in-stock")
    public Boolean isInStock(@PathVariable String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> findInventoriesBySkuCodeIn(@RequestParam List<String> skuCodes) {
        return inventoryService.findInventoriesBySkuCode(skuCodes);
    }
}
