package com.emazon.stock_service.domain.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CustomPage <T>  {

    private List<T> content;   // Contenido de la página
    private int pageNumber;          // Número de la página actual
    private int pageSize;            // Tamaño de la página
    private long totalElements;      // Número total de elementos
    private int totalPages;          // Número total de páginas
    private boolean isFirst;         // Es la primera página
    private boolean isLast;         // Es la ultima página

    public CustomPage(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages, boolean isFirst, boolean isLast) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isFirst = isFirst;
        this.isLast = isLast;
    }


}
