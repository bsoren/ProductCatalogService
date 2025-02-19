package org.example.productcatalogservice_feb2025.controllers;

import org.example.productcatalogservice_feb2025.dtos.ProductDto;
import org.example.productcatalogservice_feb2025.models.Product;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {


    @GetMapping("/products/{id}")
    public ProductDto getProductDetails(@PathVariable Long id) {
    }

    ///Get All Products
    //Delete Product
    //Patch Product (update)
    //Put Product (replace)
    //Post Product

    @PatchMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id,
                              @RequestBody ProductDto productDto) {

    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
    }


}
