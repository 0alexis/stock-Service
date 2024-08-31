package com.emazon.stock_service.configuration;


import com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.ICategoryRepository;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.api.usecase.CategoryUseCase;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

}

