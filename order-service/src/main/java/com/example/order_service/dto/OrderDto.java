package com.example.order_service.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OrderDto {
    
    List<OrderItemDto> orderItems;
}
