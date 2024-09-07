package com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.ProductEntity;
import com.emazon.stock_service.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IProductEntityMapper {
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", source = "categories")
    Product toProduct(ProductEntity productEntity);
    ProductEntity toProductEntity(Product product);
}
