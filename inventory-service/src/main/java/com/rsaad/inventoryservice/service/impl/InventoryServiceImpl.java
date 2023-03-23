package com.rsaad.inventoryservice.service.impl;

import com.rsaad.inventoryservice.dto.InventoryResponse;
import com.rsaad.inventoryservice.dto.napper.DtoMapper;
import com.rsaad.inventoryservice.repository.InventoryRepository;
import com.rsaad.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(DtoMapper::mapToInventoryDto).toList();
    }
}
