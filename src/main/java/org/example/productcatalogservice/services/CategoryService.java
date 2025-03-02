package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.State;
import org.example.productcatalogservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(CategoryDto categoryDto) {
        Category newCategory = this.from(categoryDto);
        newCategory.setState(State.ACTIVE);

        Date currentData = new Date();
        newCategory.setCreatedAt(currentData);
        newCategory.setLastUpdatedAt(currentData);

        return this.categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategory(Long categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Invalid categoryId: " + categoryId));
    }

    @Override
    public Category deleteCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Invalid categoryId: " + categoryId));

        // delete
        this.categoryRepository.deleteById(categoryId);

        return category;
    }

    @Override
    public Category replaceCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Invalid categoryId: " + categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return this.categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    private Category from(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }
}
