package com.emazon.stock_service.adapters.drivinghttp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter

public class ProductResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer amount;
    private final  Double price;
    private final BrandResponse brand;
    private final Set<ProductSetCategoryResponse> categories;
}
