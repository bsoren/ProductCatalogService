package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody  CategoryDto categoryDto) {
        Category category = this.categoryService.saveCategory(categoryDto);
        return this.from(category);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryService.getAllCategories();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(this.from(category));
        }

        return categoryDtos;
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = this.categoryService.getCategory(categoryId);
        return this.from(category);
    }

    @DeleteMapping("/{categoryId}")
    public CategoryDto deleteCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = this.categoryService.deleteCategory(categoryId);
        return this.from(category);
    }

    @PutMapping("/{categoryId}")
    public CategoryDto replaceCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryDto categoryDto
    ) {
        Category category = this.categoryService.replaceCategory(categoryId, categoryDto);
        return this.from(category);
    }

    private CategoryDto from(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }
}
