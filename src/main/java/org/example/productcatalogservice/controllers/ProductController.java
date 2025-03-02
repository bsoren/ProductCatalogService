package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.exceptions.ProductNotFoundException;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    @Qualifier("storageProductService")
    private IProductService productService;

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> allProducts = this.productService.getAllProducts();
        for (Product product: allProducts) {
            productDtos.add(from(product));
        }
        return productDtos;
    }

    @GetMapping("/products/{id}")
    public ProductDto getProductDetails(@PathVariable Long id) throws ProductNotFoundException {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid Product Id: " + id);
        }

        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("Error retrieving product with id:" + id);
        };

        return from(product);
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = this.productService.createProduct(productDto);
        return from(product);
    }

    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable Long id,
                              @RequestBody ProductDto productDto) {

        Product product = productService.replaceProduct(id, productDto);
        return from(product);
    }

    @DeleteMapping("/products/{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        Product deletedProduct = this.productService.delete(id);
        return from(deletedProduct);
    }

    private ProductDto from (Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());

        Category category = product.getCategory();
        if(category != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getId());
            categoryDto.setDescription(category.getDescription());
            productDto.setCategoryId(category.getId());
        }

        return productDto;
    }

}
