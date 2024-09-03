package com.emazon.stock_service.configuration;


import com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter.BrandMysqlAdapter;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.IBrandRepository;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.ICategoryRepository;
import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.usecase.BrandUseCase;
import com.emazon.stock_service.domain.usecase.CategoryUseCase;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort(){
        return new BrandMysqlAdapter(brandRepository, brandEntityMapper);
    }
    @Bean
    public IBrandServicePort brandServicePort(){
        return new BrandUseCase(brandPersistencePort());
    }

}

