package org.example.productcatalogservice_feb2025.services;

import org.example.productcatalogservice_feb2025.models.Product;

public interface IProductService {
    Product getProductById(Long id);
}
