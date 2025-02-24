package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();

    Product replaceProduct(Long id, ProductDto productDto);

    Product createProduct(ProductDto productDto);

    Product delete(Long id);
}
