package com.orgmihailo.productservice.service;

import com.orgmihailo.productservice.dtos.ProductRequest;
import com.orgmihailo.productservice.dtos.ProductResponse;
import com.orgmihailo.productservice.model.Product;
import com.orgmihailo.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j //this will give us logger
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .name(productRequest.getName())
                .build();

        productRepository.insert(product);
        log.info("Product "+ product.getName()+" is saved");

    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }
    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
