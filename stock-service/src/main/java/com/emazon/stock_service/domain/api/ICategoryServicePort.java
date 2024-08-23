package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {

    void saveCategory(Category category);

    Category getCategoryByName(String name);
    List<Category> getAllCategories(Integer page, Integer size);
    Category updateCategory(Category category);
    void deleteCategory(Long id);
}

