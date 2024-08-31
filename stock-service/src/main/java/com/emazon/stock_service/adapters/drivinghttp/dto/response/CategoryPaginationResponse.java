package com.emazon.stock_service.adapters.drivinghttp.dto.response;

import java.util.List;
public record CategoryPaginationResponse<T>(
        List<CategoryResponse> content,       // Contenido de la página
        int pageNumber,                       // Número de la página actual
        int pageSize,                        // Tamaño de la página
        long totalElements,                 // Número total de elementos
        int totalPages,                    // Número total de páginas
        boolean isFirst,                  // Es la primera página
        boolean isLast                   // Es la ultima página
) {
}
