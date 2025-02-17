package org.example.productcatalogservice_feb2025.controllers;

import org.example.productcatalogservice_feb2025.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {


    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable Long id) {
        Product product =new Product();
        product.setId(id);
        product.setName("Iphone 15");
        return product;
    }
}
