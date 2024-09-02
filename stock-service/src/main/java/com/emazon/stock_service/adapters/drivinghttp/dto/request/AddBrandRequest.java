package com.emazon.stock_service.adapters.drivinghttp.dto.request;

import com.emazon.stock_service.adapters.drivinghttp.dto.util.DrivingConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddBrandRequest {
    /**
     * @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
     * Valida que el campo contenga solo letras (mayúsculas y minúsculas) sin
     * espacios, números ni caracteres especiales.
     * Si la validación falla, se mostrará el mensaje:
     * "Description must contain only letters".
     */

    @NotBlank(message = DrivingConstant.FIELD_NAME_NULL_MESSEGE)
    @Size(min = 1, max = 50, message = DrivingConstant.FIELD_NAME_EXCEEDED_SIZE_MESSEGE)
    //@Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String name;

    @NotBlank(message = DrivingConstant.FIELD_DESCRIPTION_NULL_MESSEGE)
    @Size(min = 1, max = 120, message = DrivingConstant.FIELD_DESCRIPTION_EXCEEDED_SIZE_MESSEGE)
    //@Pattern(regexp = "^[a-zA-Z\\\\s]+$", message = "Description must contain only letters")
    private String description;
}
