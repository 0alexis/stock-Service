package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Category;

import java.util.List;

public interface ICategoryPersistencePort {

        void saveCategory(Category category);
        Category getCategoryByName(String name); // Para obtener una categoría específica por nombre
        List<Category> getAllCategories(Integer page, Integer size); // Para obtener todas las categorías con paginación
        Category updateCategory(Category category);
        void deleteCategory(Long id);
    }


