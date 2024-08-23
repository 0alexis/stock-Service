package com.emazon.stock_service.adapters.drivinghttp.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.emazon.stock_service.adapters.drivinghttp.dto.util.DrivingConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddCategoryRequest {

    @NotBlank(message = DrivingConstant.FIELD_NAME_NULL_MESSEGE)
    @Size(min = 1, max = 50, message = DrivingConstant.FIELD_NAME_EXCEEDED_SIZE_MESSEGE)
    private  String name;

    @NotBlank(message = DrivingConstant.FIELD_DESCRIPTION_NULL_MESSEGE)
    @Size(min = 1, max = 90, message = DrivingConstant.FIELD_DESCRIPTION_EXCEEDED_SIZE_MESSEGE)
    private  String description;
}
