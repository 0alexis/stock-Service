package com.emazon.stock_service.domain.api;

import com.emazon.stock_service.domain.model.Product;

public interface IProductServicePort {
    void saveProduct(Product product);
}