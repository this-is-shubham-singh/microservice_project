package com.example.product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.domain.Product;
import com.example.product_service.dto.ProductRequestDto;
import com.example.product_service.dto.ProductResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public String createProduct(ProductRequestDto productRequestDto) {

        Product product = Product.builder()
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .build();

        productRepository.save(product);

        log.info("product is saved", product.getId());
        return "product created Successfully";
    }

    public List<ProductResponseDto> getAllProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductResponseDto> ans = products.stream().map(product -> mapProductToProductRequestDto(product)).toList();

        return ans;
    }

    public ProductResponseDto mapProductToProductRequestDto(Product product) {

        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                                                .name(product.getName())
                                                .description(product.getDescription())
                                                .price(product.getPrice())
                                                .build();

        return productResponseDto;
    }

}
