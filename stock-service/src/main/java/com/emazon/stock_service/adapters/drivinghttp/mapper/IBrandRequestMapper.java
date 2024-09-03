package com.emazon.stock_service.adapters.drivinghttp.mapper;

import com.emazon.stock_service.adapters.drivinghttp.dto.request.*;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.SortDirection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)


public interface IBrandRequestMapper {

    @Mapping(source = "name", target = "name")
    Brand addRequestToBrand(AddBrandRequest addBrandRequest);
    SortDirection toSortDirection(SortDirectionRequest sortDirectionRequestDto);

}
