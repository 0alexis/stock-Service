package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.exception.InvalidSortDirectionException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

@RestController
@RequestMapping("/api/brands")

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {
        brandPersistencePort.saveBrand(brand);
    }


    @Override
    public CustomPage<Brand> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection,int page, int size) {
        CustomPage<Brand> customPageBrand = brandPersistencePort.getPaginationBrands(sortDirection, page, size);

        if ("ASC".equalsIgnoreCase(sortDirection.name()) || "DESC".equalsIgnoreCase(sortDirection.name())) {
            customPageBrand.setContent(
                    customPageBrand.getContent().stream()
                            .sorted("ASC".equalsIgnoreCase(sortDirection.name()) ?
                                    Comparator.comparing(Brand::getName) :
                                    Comparator.comparing(Brand::getName).reversed())
                            .toList()
            );
        }   else {
            throw new InvalidSortDirectionException();
        }

        return customPageBrand;
    }

//    private Brand getBrandFindByName(String name) {
//        return brandPersistencePort.findByName(name);
//    }

}
