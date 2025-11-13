package com.example.inventory_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_service.dto.InventoryResponseDto;
import com.example.inventory_service.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)  
    public List<InventoryResponseDto> inStock (@RequestParam List<String> skuCodes) {
        System.out.println("*******************************************");
        System.out.println(skuCodes);
        System.out.println("*******************************************");
        return inventoryService.inStock(skuCodes);
    }   
}
