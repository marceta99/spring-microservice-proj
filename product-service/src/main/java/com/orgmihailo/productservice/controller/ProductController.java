package com.orgmihailo.productservice.controller;

import com.orgmihailo.productservice.dtos.ProductRequest;
import com.orgmihailo.productservice.dtos.ProductResponse;
import com.orgmihailo.productservice.model.Product;
import com.orgmihailo.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService ;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }




}
