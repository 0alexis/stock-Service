package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;


public interface ICategoryPersistencePort {

    void saveCategory(Category category);
    Category findByName(String name);
    //CustomPage<Category> getPaginationCategories(int page, int size);
    CustomPage<Category> getPaginationCategories(SortDirection sortDirection, int page, int size);
}



