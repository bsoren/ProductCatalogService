package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.repositories.CategoryRepository;
import org.example.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("storageProductService")
public class StorageProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public StorageProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        return productOptional.
                orElseThrow(() -> new RuntimeException("Product with id: " + id + " doesn't exists"));
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product replaceProduct(Long id, ProductDto productDto) {
        // check if product exists
        Product existingProduct = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id: " + id + " doesn't exists"));

        // check if category exists
        Long categoryId = productDto.getCategoryId();
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Invalid categoryId: " + categoryId));

        // update all fields of existing product
        existingProduct.setCategory(category);
        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setImageUrl(productDto.getImageUrl());

        // save and return updated product
        return this.productRepository.save(existingProduct);
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Long categoryId = productDto.getCategoryId();
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = this.from(productDto);
        product.setCategory(category);

        return this.productRepository.save(product);
    }

    @Override
    public Product delete(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid productId: " + id));

        this.productRepository.deleteById(id);

        return product;

    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        return product;
    }
}
