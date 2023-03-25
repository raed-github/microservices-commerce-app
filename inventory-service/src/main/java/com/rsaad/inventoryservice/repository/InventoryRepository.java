package com.rsaad.inventoryservice.repository;

import com.rsaad.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findIBySkuCodeIn(List<String> skuCodes);
    Optional<Inventory> findBySkuCode(String skuCodes);
}
