package com.emazon.stock_service.domain.spi;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;

public interface IBrandPersistencePort {
    void saveBrand(Brand brand);
    Brand findByName(String name);
    CustomPage<Brand> getPaginationBrands(SortDirection sortDirection, int page, int size);

}
