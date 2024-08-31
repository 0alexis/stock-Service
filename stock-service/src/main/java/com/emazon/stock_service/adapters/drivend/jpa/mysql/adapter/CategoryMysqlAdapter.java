package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.CategoryEntity;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.ElementNotFoundException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.ICategoryRepository;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CategoryMysqlAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;



    @Override
    public void saveCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).stream().findFirst().isPresent()){
            throw new CategoryAlreadyExistsException("A category with this name already exists.");
        }
        categoryRepository.save(categoryEntityMapper.toCategoryEntity(category));
    }


    @Override
    public Category findByName(String CategoryName) {
        return categoryRepository.findByName(CategoryName)
                .map(categoryEntityMapper::toCategory)
                .orElse(null);
    }


    @Override
    public CustomPage<Category> getPaginationCategories(SortDirection sortDirection, int page, int size) {

        //Pageable pageable = PageRequest.of( page, size);
        //Pageable pageable = PageRequest.of(page, size, sortDirection == SortDirection.ASC ? Sort.by("name").ascending() : Sort.by("name").descending());

        Sort.Direction direction = (sortDirection == SortDirection.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort.Order order = new Sort.Order(direction, "name");

        Pageable pageable = PageRequest.of(page, size, Sort.by(order));


        Page<CategoryEntity> categoriesPage = categoryRepository.findAll(pageable);

        if(categoriesPage.isEmpty()) {
            throw new ElementNotFoundException();
        }

        List<Category> categoryContent = categoriesPage.getContent()
                .stream()
                .map(category -> new Category(category.getId(), category.getName(), category.getDescription()))
                .toList();
//        CustomPage<Category> customPage =
        return new CustomPage<>(
                categoryContent,
                categoriesPage.getNumber(),
                categoriesPage.getSize(),
                categoriesPage.getTotalElements(),
                categoriesPage.getTotalPages(),
                categoriesPage.isFirst(),
                categoriesPage.isLast()
        );
    }
}
