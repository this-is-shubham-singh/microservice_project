package com.example.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.order_service.domain.Order;
import com.example.order_service.domain.OrderItem;
import com.example.order_service.dto.InventoryReponseDto;
import com.example.order_service.dto.OrderDto;
import com.example.order_service.dto.OrderItemDto;
import com.example.order_service.respository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderDto orderDto) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        
        // recieving all orderItemdto's so converting them to OrderItem to be set on order db 
        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                .map(orderItem -> mapOrderItemDtoToOrderItem(orderItem)).toList();

        order.setOrderItems(orderItems);

        // get all the skucodes to send it to inventory service to verify. 
        List<String> skuCodes = orderItems.stream().map(orderItem -> orderItem.getSkuCode()).toList();

        System.out.println("**************************************************************");
        System.out.println(skuCodes);
        System.out.println("**************************************************************");


        // pass all the skuCodes and check if the selected items is in stock or not
        List<InventoryReponseDto> inventoryResponseList = webClientBuilder.build().get().uri("http://inventory-service/api/inventory", uriBuilder ->  uriBuilder.queryParam("skuCodes", skuCodes).build()).retrieve().bodyToFlux(InventoryReponseDto.class).collectList().block();
        
        if(inventoryResponseList.size() == 0) {
            return "product is out of stock";
        }

        boolean result = inventoryResponseList.stream().allMatch(inventoryResponse -> inventoryResponse.isInStock());        

        System.out.println("**************************");
        System.out.println(result);
        System.out.println("**************************");

        if(result) {
            orderRepository.save(order);
            return "order placed succesfully";
        }

        return "product out of stock";
    }

    public OrderItem mapOrderItemDtoToOrderItem(OrderItemDto orderItemDto) {

        OrderItem orderItem = OrderItem.builder().skuCode(orderItemDto.getSkuCode())
                .quantity(orderItemDto.getQuantity()).price(orderItemDto.getPrice()).build();

        return orderItem;
    }

}
