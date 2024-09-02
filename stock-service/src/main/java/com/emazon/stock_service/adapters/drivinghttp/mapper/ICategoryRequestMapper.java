package com.emazon.stock_service.adapters.drivinghttp.mapper;

import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddCategoryRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.*;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.SortDirection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ICategoryRequestMapper {


//    @Mapping(target = "id", ignore = true)
//    @Mapping(source = "description", target =

      @Mapping(source = "name", target = "name")
    Category addRequestToCategory(AddCategoryRequest addCategoryRequest);
    SortDirection toSortDirection(SortDirectionRequest sortDirectionRequestDto);

/*
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
el mismo metodo de actualizar que aun no se esta usando
 Category UpdateCategoryRequest(UpdateCategoryRequest updateCategoryRequest);
*/

}
