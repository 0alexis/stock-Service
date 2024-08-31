package com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.CategoryEntity;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryPaginationResponse;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ICategoryEntityMapper {

    Category toCategory(CategoryEntity categoryEntity);
    CategoryEntity toCategoryEntity(Category category);
    CategoryPaginationResponse toCategoryPaginationResponse(CategoryEntity categoryEntity);
    CustomPage toCustomPage(CategoryEntity categoryEntity);

//    List<Category> toModelList(List<CategoryEntity> categoryEntities);
}
