package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.CategoryEntity;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.ElementNotFoundException;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import com.emazon.stock_service.domain.util.DomainConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.ICategoryRepository;
import com.emazon.stock_service.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;


import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryMysqlAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryMysqlAdapter categoryMysqlAdapter;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryMysqlAdapter = new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }


    @Test
    void saveCategory_ShouldSaveCategorySuccessfully_WhenCategoryDoesNotExist() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Electronic gadgets");
        category.setName("Electronics");
        category.setDescription("Devices");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryEntityMapper.toCategoryEntity(category)).thenReturn(categoryEntity);

        // Act
        categoryMysqlAdapter.saveCategory(category);

        // Assert
        verify(categoryRepository, times(1)).save(categoryEntity);
    }
    @Test
     void findByName_CategoryExists_ShouldReturnCategory() {
        // Arrange
        String categoryName = "Electronics";
        Category category = new Category(1L, "Electronics", "Electronic gadgets");
        category.setName(categoryName);
        category.setDescription("Devices");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Act
        Category result = categoryMysqlAdapter.findByName(categoryName);

        // Assert
        assertEquals(category, result);
    }
    @Test
     void saveCategory_ShouldHandleException() {
        //Arrange
        Category category = new Category(1L, "Electronics", "Electronic gadgets");
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronic gadgets");
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(categoryEntity) );

        //ACT
        assertThrows(CategoryAlreadyExistsException.class, () ->
                categoryMysqlAdapter.saveCategory(category)
               );
    }
    @Test
     void getPaginationCategories_NoCategories_ShouldThrowElementNotFoundException() {
    // Arrange
    SortDirection sortDirection = SortDirection.ASC; // Configura la dirección de ordenación
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name"))); // Configura el Pageable
    Page<CategoryEntity> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0); // Crea una página vacía

    when(categoryRepository.findAll(any(Pageable.class))).thenReturn(emptyPage); // Simula el retorno de una página vacía

    // Act & Assert
    assertThrows(ElementNotFoundException.class, () -> {
        categoryMysqlAdapter.getPaginationCategories(sortDirection, 0, 10);
    });
}
    @Test
     void getPaginationCategories_ShouldReturnCustomPage() {
        // Arrange
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Devices");
        List<CategoryEntity> categoryEntities = List.of(categoryEntity);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name")));
        Page<CategoryEntity> categoriesPage = new PageImpl<>(categoryEntities, pageable, categoryEntities.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoriesPage);

        // Act
        CustomPage<Category> result = categoryMysqlAdapter.getPaginationCategories(SortDirection.ASC, 0, 10);

        // Assert
        assertEquals(0, categoriesPage.getNumber());
        assertEquals(10, categoriesPage.getSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertEquals(1, result.getContent().size());

    }
    @Test
    void getPaginationCategories_ShouldThrowElementNotFoundException_WhenNoCategoriesFound() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name")));
        Page<CategoryEntity> categoriesPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoriesPage);

        // Act & Assert
        assertThrows(ElementNotFoundException.class, () -> {
             categoryMysqlAdapter.getPaginationCategories(SortDirection.ASC, 0, 10);
        });
    }
    @Test
    void getPaginationCategories_ShouldReturnCustomPage_WhenSortedDescending() {
        // Arrange
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Devices");
        List<CategoryEntity> categoryEntities = List.of(categoryEntity);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("name")));
        Page<CategoryEntity> categoriesPage = new PageImpl<>(categoryEntities, pageable, categoryEntities.size());

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoriesPage);

        // Act
        CustomPage<Category> result =  categoryMysqlAdapter.getPaginationCategories(SortDirection.DESC, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertEquals("Electronics", result.getContent().get(0).getName());
    }
    @Test
    void testCategoryConstructor_WithNullDescription_ShouldThrowException() {
        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Category(1L, "Gadgets", null);
        });

        assertEquals(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE, exception.getMessage());
    }
//    @Test
//    void testCategoryConstructor_WithEmptyName_ShouldThrowException() {
//        // Act & Assert
//        Exception exception = assertThrows(EmptyFieldException.class, () -> {
//            new Category(1L, "", "Gadgets description");
//        });
//
//        assertEquals(DomainConstants.Field.NAME.toString(), exception.getMessage());
//    }
//    @Test
//    void testCategoryConstructor_WithNameContainingOnlySpaces_ShouldThrowException() {
//        // Act & Assert
//        Exception exception = assertThrows(EmptyFieldException.class, () -> {
//            new Category(1L, "   ", "Gadgets description");
//        });
//
//        assertEquals(DomainConstants.Field.NAME.toString(), exception.getMessage());
//    }
    @Test
    void testCategoryConstructor_WithValidInputs_ShouldCreateCategory() {
        // Act
        Category category = new Category(1L, "Gadgets", "Gadgets description");

        // Assert
        assertEquals(1L, category.getId());
        assertEquals("Gadgets", category.getName());
        assertEquals("Gadgets description", category.getDescription());
    }
}
