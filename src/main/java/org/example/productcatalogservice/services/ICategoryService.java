package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.models.Category;

import java.util.List;

public interface ICategoryService {

    Category saveCategory(CategoryDto categoryDto);

    Category getCategory(Long categoryId);

    Category deleteCategory(Long categoryId);

    Category replaceCategory(Long categoryId, CategoryDto categoryDto);

    List<Category> getAllCategories();

}

