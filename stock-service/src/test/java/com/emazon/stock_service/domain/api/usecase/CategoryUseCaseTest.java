package com.emazon.stock_service.domain.api.usecase;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.emazon.stock_service.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private CategoryMysqlAdapter categoryMysqlAdapter;

    @InjectMocks
    private CategoryUseCase categoryUseCase;


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryUseCase).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSaveCategory_whenTheCategoryIsSuccessfullySaved() {
        // Arrange
        Category category = new Category(1L, "Electronics", "All kinds of electronics");

        // Act
        categoryUseCase.saveCategory(category);

        // Assert
        verify(categoryMysqlAdapter, times(1)).saveCategory(any(Category.class));
    }

    @Test
    void testSaveCategory_whenPersistenceFails_shouldThrowException() {
        // Arrange
        Category category = new Category(1L, "Toys", "Various toys");

        doThrow(new RuntimeException("Database connection error"))
                .when(categoryMysqlAdapter).saveCategory(any(Category.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals("Database connection error", exception.getMessage());
    }
    @Test
    void testSaveCategory_whenNameAlreadyExists_shouldThrowException() {
        // Arrange
        Category category = new Category(1L, "Alexis","Alexis´s description");
        category.setName("ExistingCategory");
        category.setDescription("Description for existing category");

        doThrow(new RuntimeException("A category with this name already exists."))
                .when(categoryMysqlAdapter).saveCategory(any(Category.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        // Verifica el mensaje de la excepción
        assertEquals("A category with this name already exists.", exception.getMessage());
    }

    @Test
    void testSaveCategory_whenValidCategory_shouldSaveSuccessfully() {
        // Arrange
        Category category = new Category(1L, "ValidCategory", "Valid description");

        // Act
        categoryUseCase.saveCategory(category);

        // Assert
        verify(categoryMysqlAdapter, times(1)).saveCategory(category);
    }
    @Test
    void testSaveCategory_whenPersistencePortThrowsException_shouldPropagateException() {
        // Arrange
        Category category = new Category(1L, "ValidName", "Valid description");

        doThrow(new RuntimeException("Unexpected error")).when(categoryMysqlAdapter).saveCategory(any(Category.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals("Unexpected error", exception.getMessage());
        verify(categoryMysqlAdapter, times(1)).saveCategory(category);
    }




}
