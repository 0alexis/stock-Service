package com.emazon.stock_service.adapters.drivinghttp.mapper;


import com.emazon.stock_service.adapters.drivinghttp.dto.response.BrandPaginationResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.BrandResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryPaginationResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryResponse;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
    Brand toBrand(BrandResponse brandsResponse);
    BrandPaginationResponse<BrandResponse> toBrandPaginationResponse(CustomPage<Brand> brandEntity);
    CustomPage<Brand> toCustomPage(BrandPaginationResponse brandPaginationResponse);

}
