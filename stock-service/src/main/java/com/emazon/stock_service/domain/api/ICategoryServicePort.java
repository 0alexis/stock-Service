package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;


public interface ICategoryServicePort {

    void saveCategory(Category category);
    CustomPage<Category> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection, int page, int size);

}

