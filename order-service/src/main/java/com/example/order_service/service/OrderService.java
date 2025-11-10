package com.example.order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.order_service.domain.Order;
import com.example.order_service.domain.OrderItem;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderItemDto;
import com.example.order_service.respository.OrderRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public String placeOrder(OrderDto orderDto) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                .map(orderItem -> mapOrderItemDtoToOrderItem(orderItem)).toList();

        order.setOrderItems(orderItems);

        orderRepository.save(order);

        return "order placed succesfully";
    }

    public OrderItem mapOrderItemDtoToOrderItem(OrderItemDto orderItemDto) {

        OrderItem orderItem = OrderItem.builder().skuCode(orderItemDto.getSkuCode())
                .quantity(orderItemDto.getQuantity()).price(orderItemDto.getPrice()).build();

        return orderItem;
    }

}
