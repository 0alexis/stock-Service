package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;


import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.CategoryEntity;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.ElementNotFoundException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.NoDataFoundException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.ICategoryRepository;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

@RequiredArgsConstructor
public class CategoryMysqlAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;



    @Override
    public void saveCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).stream().findFirst().isPresent()){
            throw new CategoryAlreadyExistsException();
        }
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }


    @Override
    public Category getCategoryByName(String name) {
        CategoryEntity categoryEntity = categoryRepository.findByName(name)
                .stream().findAny().orElseThrow(ElementNotFoundException::new);

        return categoryEntityMapper.toModel(categoryEntity);
    }


    @Override
    public List<Category> getAllCategories(Integer page, Integer size) {
        Pageable pagination = PageRequest.of(page, size);
        List<CategoryEntity> categoryEntities = categoryRepository.findAll(pagination).getContent();
        if (categoryEntities.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toModelList(categoryEntities);
    }


    @Override
    public Category updateCategory(Category category) {
        if(categoryRepository.findById(category.getId()).isEmpty()){
            throw  new ElementNotFoundException();
        }
        return categoryEntityMapper.toModel(categoryRepository.save(categoryEntityMapper.toEntity(category)));
    }

    @Override
    public void deleteCategory(Long id) {
        if(categoryRepository.findById(id).isPresent()){
            //se lanza una excepción si la categoría existe, pero debería lanzarse si NO existe

        }
         categoryRepository.deleteById(id);
    }
}
