package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Category;

import java.util.Optional;


public interface ICategoryPersistencePort {

    Optional<Category> findByName(String name);
        void saveCategory(Category category);

    }


