package com.emazon.stock_service.adapters.drivinghttp.mapper;

import com.emazon.stock_service.adapters.drivinghttp.dto.response.ProductResponse;
import com.emazon.stock_service.domain.model.Product;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IProductResponseMapper {
    ProductResponse toProdcutResponse(Product product);
    Product toProduct(ProductResponse productResponse);

}
