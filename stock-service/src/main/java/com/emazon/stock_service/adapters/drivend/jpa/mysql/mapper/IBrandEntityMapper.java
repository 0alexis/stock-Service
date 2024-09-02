package com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.BrandEntity;
import com.emazon.stock_service.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)


public interface IBrandEntityMapper {
    Brand toBrand(BrandEntity brandEntity);
    BrandEntity toBrandEntity(Brand brand);

}
