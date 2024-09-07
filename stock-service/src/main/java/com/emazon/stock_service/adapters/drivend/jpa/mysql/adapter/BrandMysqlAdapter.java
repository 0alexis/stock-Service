package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;


import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.BrandEntity;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.BrandAlreadyExistsException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.ElementNotFoundException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.IBrandRepository;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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
    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .map(brandEntityMapper::toBrand)
                .orElse(null);
    }


    @Override
    public CustomPage<Brand> getPaginationBrands(SortDirection sortDirection, int page, int size) {

        Sort.Direction direction = (sortDirection == SortDirection.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort.Order order = new Sort.Order(direction, "name");

        Pageable pageable = PageRequest.of(page, size, Sort.by(order));


        Page<BrandEntity> BrandsPage = brandRepository.findAll(pageable);

        if(BrandsPage.isEmpty()) {
            throw new ElementNotFoundException();
        }

        List<Brand> brandContent = BrandsPage.getContent()
                .stream()
                .map(brand -> new Brand(brand.getId(), brand.getName(), brand.getDescription()))
                .toList();
        return new CustomPage<>(
                brandContent,
                BrandsPage.getNumber(),
                BrandsPage.getSize(),
                BrandsPage.getTotalElements(),
                BrandsPage.getTotalPages(),
                BrandsPage.isFirst(),
                BrandsPage.isLast()
        );
    }
}
