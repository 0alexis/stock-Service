package com.emazon.stock_service.adapters.drivinghttp.dto.request;

import com.emazon.stock_service.adapters.drivinghttp.dto.util.DrivingConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    /**
     * @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
     * Valida que el campo contenga solo letras (mayúsculas y minúsculas) sin
     * espacios, números ni caracteres especiales.
     * Si la validación falla, se mostrará el mensaje:
     * "Description must contain only letters".
     */

    @NotBlank(message = DrivingConstant.FIELD_NAME_NULL_MESSEGE)
    //@Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String name;

    @NotBlank(message = DrivingConstant.FIELD_DESCRIPTION_NULL_MESSEGE)
    //@Pattern(regexp = "^[a-zA-Z\\\\s]+$", message = "Description must contain only letters")
    private String description;
    //@NotBlank(message = DrivingConstant.FIELD_DESCRIPTION_NULL_MESSEGE)
    private Integer amount;

    //@NotBlank(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 6,
            fraction = 2,
            message = "Price must be a valid number with up to 6 integer digits and 2 fractional digits")
    private Double price;

    //@NotNull(message = "Brand ID is required")
    @Positive(message = "Brand ID must be a positive number")
    private Long brandId;

   //@NotBlank(message = "Categories list must not be empty")
    @Size(min = 1, max = 3, message = "The categories must contain between 1 and 3 items")
    @UniqueElements(message = "The categories list must not contain repeated items")
    List<@NotNull(message = "Category ID cannot be null")
        @Positive(message = "Category ID must be a positive number")
                Long
                >
             categoryId;
}
