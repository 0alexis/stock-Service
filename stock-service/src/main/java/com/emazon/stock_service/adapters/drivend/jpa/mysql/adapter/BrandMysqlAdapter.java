package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;


import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.BrandAlreadyExistsException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.IBrandRepository;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandMysqlAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;


    @Override
    public void saveBrand(Brand brand) {
        if (brandRepository.findByName(brand.getName()).stream().findFirst().isPresent()){
            throw new BrandAlreadyExistsException("A brand with this name already exists.");
        }
        brandRepository.save(brandEntityMapper.toBrandEntity(brand));
    }

    @Override
    public Brand findByName(String BrandName) {
        return brandRepository.findByName(BrandName)
                .map(brandEntityMapper::toBrand)
                .orElse(null);
    }
}
