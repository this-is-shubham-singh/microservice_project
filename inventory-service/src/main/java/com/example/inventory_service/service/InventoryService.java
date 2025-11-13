package com.example.inventory_service.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.inventory_service.domain.Inventory;
import com.example.inventory_service.dto.InventoryResponseDto;
import com.example.inventory_service.repository.InventoryRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponseDto> inStock(List<String> skuCodes) {

        List<Inventory> inventoryList = inventoryRepository.findByskuCodeIn(skuCodes);

        System.out.println("****************************************************");
        System.out.println(inventoryList);
        System.out.println("****************************************************");
        
        List<InventoryResponseDto> response = inventoryList.stream().map(inventoryItem -> InventoryResponseDto.builder().skuCode(inventoryItem.getSkuCode()).inStock(inventoryItem.getQuantity() > 0).build()).toList();

        System.out.println("******************************************************");
        System.out.println(response);
        System.out.println("******************************************************");

        return response;
    }

}
