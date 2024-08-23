package com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.CategoryEntity;
import com.emazon.stock_service.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;



@Mapper(componentModel = "spring")

public interface ICategoryEntityMapper {
    // @Mapping(source = "id", target = "id")
    // @Mapping(source = "name", target = "name")

    Category toModel(CategoryEntity categoryEntity);
    // @Mapping(source = "id", target = "id")
    // @Mapping(source = "name", target = "name")

    CategoryEntity toEntity(Category category);
    List<Category> toModelList(List<CategoryEntity> categoryEntities);
}
