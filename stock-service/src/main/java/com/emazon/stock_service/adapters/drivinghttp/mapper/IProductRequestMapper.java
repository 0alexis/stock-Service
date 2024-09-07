package com.emazon.stock_service.adapters.drivinghttp.mapper;

import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddProductRequest;
import com.emazon.stock_service.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)


public interface IProductRequestMapper {
    @Mapping(source = "name", target = "name")
    Product addRequestToProduct(AddProductRequest addProductRequest);

}
