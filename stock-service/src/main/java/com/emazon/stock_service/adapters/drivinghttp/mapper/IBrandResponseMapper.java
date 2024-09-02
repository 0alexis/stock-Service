package com.emazon.stock_service.adapters.drivinghttp.mapper;


import com.emazon.stock_service.adapters.drivinghttp.dto.response.BrandResponse;
import com.emazon.stock_service.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
    Brand toBrand(BrandResponse brandsResponse);
}
