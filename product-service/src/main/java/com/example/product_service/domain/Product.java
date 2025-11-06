package com.example.product_service.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(value = "product")
@Builder
@Data
public class Product {
    
    @Id      // this only marks the field as documents primary key
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
