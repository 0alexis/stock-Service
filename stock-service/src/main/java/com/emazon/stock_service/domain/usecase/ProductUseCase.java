package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.api.IProductServicePort;
import com.emazon.stock_service.domain.exception.ProductCannotHaveMoreThanThreeCategoriesException;
import com.emazon.stock_service.domain.exception.ProductCategoryRepeatedException;
import com.emazon.stock_service.domain.exception.ProductMustHaveAtLeastOneCategoryException;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;


    public ProductUseCase(IProductPersistencePort productPersistencePort,ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort){
        this.productPersistencePort = productPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveProduct(Product product) {
        product.setCategories(getCategoryByIds(product.getCategoryId()));
        product.setBrand(brandPersistencePort.getBrandById(product.getBrandId()));



        productPersistencePort.saveProduct(product);
    }


        private Set<Category> getCategoryByIds(List<Long> categoryIds) {
            if (categoryIds == null || categoryIds.isEmpty()) {
                throw new ProductMustHaveAtLeastOneCategoryException();
            }
     //si tiene mas de 3 categorias lanza una exception
        if (categoryIds.size() > 3) {
            throw new ProductCannotHaveMoreThanThreeCategoriesException();
        }

       //set no acepta valores duplicados para que los valores seans
        Set<Long> uniqueCategories = new HashSet<>(categoryIds);
        if (uniqueCategories.size() != categoryIds.size()) {
            throw new ProductCategoryRepeatedException();
        }

        return categoryIds.stream()
                .map(categoryPersistencePort::getCategoryById)
                .collect(Collectors.toSet());
    }


}
