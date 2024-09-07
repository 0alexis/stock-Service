package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;

public interface IBrandServicePort {
    void saveBrand(Brand brand);
    Brand getBrandById(Long id);
    CustomPage<Brand> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection, int page, int size);
}

