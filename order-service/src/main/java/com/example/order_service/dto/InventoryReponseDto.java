package com.example.order_service.dto;

import lombok.Data;

@Data
public class InventoryReponseDto {
    private String skuCode;
    private boolean inStock;
}
