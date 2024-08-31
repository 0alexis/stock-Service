package com.emazon.stock_service.adapters.drivinghttp.mapper;


import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryPaginationResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryResponse;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
//    @Mapping(source = "id", target = "userId")
//    @Mapping(source = "name", target = "userName")
//    @Mapping(source = "caracter", target = "userCaracter")

    CategoryResponse toCategoryResponse(Category category);
    Category toCategory(CategoryResponse categoriesResponse);
    CategoryPaginationResponse<CategoryResponse> toCategoryPaginationResponse(CustomPage<Category> categoryEntity);
    CustomPage<Category> toCustomPage(CategoryPaginationResponse categoryPaginationResponse);
}
