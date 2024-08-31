package com.emazon.stock_service.adapters.drivinghttp.dto.request;

import com.emazon.stock_service.adapters.drivinghttp.dto.util.DrivingConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class UpdateCategoryRequest {
    private final Long id;

    @NotBlank(message = DrivingConstant.FIELD_NAME_NULL_MESSEGE)
    @Size(max = 50, message = DrivingConstant.FIELD_NAME_EXCEEDED_SIZE_MESSEGE)
    private final String name;

    @NotBlank(message = DrivingConstant.FIELD_DESCRIPTION_NULL_MESSEGE)
    @Size(max = 50, message = DrivingConstant.FIELD_DESCRIPTION_EXCEEDED_SIZE_MESSEGE)
    private final String description;
}
