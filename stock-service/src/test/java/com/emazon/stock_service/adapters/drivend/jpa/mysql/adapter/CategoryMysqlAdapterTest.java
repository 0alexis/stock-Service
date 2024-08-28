package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.CategoryEntity;
import com.emazon.stock_service.domain.exception.EmptyFieldException;
import com.emazon.stock_service.domain.util.DomainConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;


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

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryMysqlAdapter).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void saveCategory_ShouldSaveCategorySuccessfully_WhenCategoryDoesNotExist() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Electronic gadgets");
        when(categoryRepository.findByName(category.getName())).thenReturn(Collections.emptyList());
        when(categoryEntityMapper.toEntity(category)).thenReturn(new CategoryEntity(1L, "Electronics", "Electronic gadgets"));

        // Act
        categoryMysqlAdapter.saveCategory(category);

        // Assert
        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));
    }
    @Test
    void saveCategory_ShouldThrowException_WhenCategoryAlreadyExists() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Electronic gadgets");
        when(categoryRepository.findByName(category.getName())).thenReturn(Collections.singletonList(new CategoryEntity(1L, "Electronics", "Electronic gadgets")));

        // Act & Assert
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryMysqlAdapter.saveCategory(category));
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }
    @Test
    void saveCategory_ShouldThrowException_WhenCategoryNameExistsWithDifferentCase() {
        // Arrange
        Category category = new Category(1L, "electronics", "Category for electronics");
        CategoryEntity existingEntity = new CategoryEntity(2L, "Electronics", "Another description");

        when(categoryRepository.findByName(category.getName())).thenReturn(Collections.singletonList(existingEntity));

        // Act & Assert
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryMysqlAdapter.saveCategory(category));
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }
    @Test
    void saveCategory_ShouldHandleCategoryWithNullDescription() {
        // Arrange
        Category category = new Category(1L, "Gadgets", "");
        CategoryEntity entityToSave = new CategoryEntity(1L, "Gadgets", null);

        when(categoryRepository.findByName(category.getName())).thenReturn(Collections.emptyList());
        when(categoryEntityMapper.toEntity(category)).thenReturn(entityToSave);

        // Act
        categoryMysqlAdapter.saveCategory(category);

        // Assert
        verify(categoryRepository, times(1)).save(entityToSave);
    }
    @Test
    void testCategoryConstructor_WithNullDescription_ShouldThrowException() {
        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Category(1L, "Gadgets", null);
        });

        assertEquals(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE, exception.getMessage());
    }
    @Test
    void testCategoryConstructor_WithEmptyName_ShouldThrowException() {
        // Act & Assert
        Exception exception = assertThrows(EmptyFieldException.class, () -> {
            new Category(1L, "", "Gadgets description");
        });

        assertEquals(DomainConstants.Field.NAME.toString(), exception.getMessage());
    }
    @Test
    void testCategoryConstructor_WithNameContainingOnlySpaces_ShouldThrowException() {
        // Act & Assert
        Exception exception = assertThrows(EmptyFieldException.class, () -> {
            new Category(1L, "   ", "Gadgets description");
        });

        assertEquals(DomainConstants.Field.NAME.toString(), exception.getMessage());
    }
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
