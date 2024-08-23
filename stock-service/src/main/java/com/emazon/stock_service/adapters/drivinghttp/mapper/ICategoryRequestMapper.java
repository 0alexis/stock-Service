package com.emazon.stock_service.adapters.drivinghttp.mapper;

import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddCategoryRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.*;
import com.emazon.stock_service.domain.model.Category;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {


//    @Mapping(target = "id", ignore = true)
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "description", target = "description")

    Category addRequestToCategory(AddCategoryRequest addCategoryRequest);

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "description", target = "description")

    Category updateRequestToCategory(UpdateCategoryRequest updateCategoryRequest);

}
