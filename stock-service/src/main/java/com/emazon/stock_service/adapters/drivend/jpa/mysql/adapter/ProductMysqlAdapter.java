package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.IProductRepository;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ProductMysqlAdapter implements IProductPersistencePort{

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;


    @Override
    public void saveProduct(Product product) {
        productRepository.save(productEntityMapper.toProductEntity(product));
    }
}
