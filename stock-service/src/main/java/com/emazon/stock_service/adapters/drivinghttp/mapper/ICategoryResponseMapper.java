package com.emazon.stock_service.adapters.drivinghttp.mapper;


import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryResponse;
import com.emazon.stock_service.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
//    @Mapping(source = "id", target = "userId")
//    @Mapping(source = "name", target = "userName")
//    @Mapping(source = "caracter", target = "userCaracter")

    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}
