package com.emazon.stock_service.adapters.drivinghttp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public class CategoryResponse {
    private final Long id;
    private final String name;
    private final String description;
}
