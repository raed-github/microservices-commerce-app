package com.rsaad.orderservice.client;

import com.rsaad.orderservice.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/inventories/{skuCode}")
    boolean checkStock(@PathVariable String skuCode);
}
