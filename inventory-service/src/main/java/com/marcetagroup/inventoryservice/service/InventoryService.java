package com.marcetagroup.inventoryservice.service;

import com.marcetagroup.inventoryservice.dto.InventoryResponse;
import com.marcetagroup.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes){
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory ->
                                InventoryResponse.builder()
                                        .skuCode(inventory.getSkuCode())
                                        .isInStock(inventory.getQuantity() > 0)
                                        .build()
                        ).toList();
    }



}
