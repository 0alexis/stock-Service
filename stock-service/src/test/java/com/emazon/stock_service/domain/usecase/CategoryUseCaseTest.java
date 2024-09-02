package com.emazon.stock_service.domain.usecase;


import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.emazon.stock_service.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategorySuccess() {
    // Arrange
    Category category = new Category(1L, "Books", "Varius books");
    category.setName("Valid Name");
    category.setDescription("Valid Description");
    when(categoryPersistencePort.findByName(category.getName())).thenReturn(null);

    // Act & Assert
    assertDoesNotThrow(() -> categoryUseCase.saveCategory(category));
    verify(categoryPersistencePort, times(1)).saveCategory(category);
}

    @Test
    void testSaveCategory_CategoryAlreadyExists() {
    //GIVEN
    Category category = new Category(1L, "Books", "Varius books");

    categoryPersistencePort.saveCategory(category);
    verify(categoryPersistencePort, times(1)).saveCategory(category);
}
    @Test
    void testSaveCategory_whenPersistenceFails_shouldThrowException() {
        // Arrange
        Category category = new Category(1L, "Toys", "Various toys");

        doThrow(new RuntimeException("Database connection error"))
                .when(categoryPersistencePort).saveCategory(any(Category.class));

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
                .when(categoryPersistencePort).saveCategory(any(Category.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        // Verifica el mensaje de la excepción
        assertEquals("A category with this name already exists.", exception.getMessage());
    }
    @Test
    void testSaveCategory_whenPersistencePortThrowsException_shouldPropagateException() {
        // Arrange
        Category category = new Category(1L, "ValidName", "Valid description");

        doThrow(new RuntimeException("Unexpected error")).when(categoryPersistencePort).saveCategory(any(Category.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals("Unexpected error", exception.getMessage());
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }
    @Test
    void testGetPaginationCategoriesByAscAndDesc_whenSortDirectionIsAsc_shouldReturnSortedCategoriesAscending() {
        // Arrange
        Category category1 = new Category(1L, "Books", "Various books");
        Category category2 = new Category(2L, "Electronics", "All kinds of electronics");
        Category category3 = new Category(3L, "Toys", "Various toys");

        List<Category> categoryList = Arrays.asList(category1, category2, category3);
        CustomPage<Category> customPage = new CustomPage<>(categoryList, 0, 10, 3L, 1, true, true);

        when(categoryPersistencePort.getPaginationCategories(SortDirection.ASC, 0, 10)).thenReturn(customPage);

        // Act
        CustomPage<Category> result = categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.ASC, 0, 10);

        // Assert
        List<Category> sortedCategories = result.getContent();
        assertEquals(3, sortedCategories.size());
        assertEquals("Books", sortedCategories.get(0).getName());
        assertEquals("Electronics", sortedCategories.get(1).getName());
        assertEquals("Toys", sortedCategories.get(2).getName());

        verify(categoryPersistencePort, times(1)).getPaginationCategories(SortDirection.ASC, 0, 10);
    }
    @Test
    void testGetPaginationCategoriesByAscAndDesc_whenSortDirectionIsDesc_shouldReturnSortedCategoriesDescending() {
        // Arrange
        Category category1 = new Category(1L, "Books", "Various books");
        Category category2 = new Category(2L, "Electronics", "All kinds of electronics");
        Category category3 = new Category(3L, "Toys", "Various toys");

        List<Category> categoryList = Arrays.asList(category1, category2, category3);
        CustomPage<Category> customPage = new CustomPage<>(categoryList, 0, 10, 3L, 1, true, true);

        when(categoryPersistencePort.getPaginationCategories(SortDirection.DESC, 0, 10)).thenReturn(customPage);

        // Act
        CustomPage<Category> result = categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.DESC, 0, 10);

        // Assert
        List<Category> sortedCategories = result.getContent();
        assertEquals(3, sortedCategories.size());
        assertEquals("Toys", sortedCategories.get(0).getName());
        assertEquals("Electronics", sortedCategories.get(1).getName());
        assertEquals("Books", sortedCategories.get(2).getName());

        verify(categoryPersistencePort, times(1)).getPaginationCategories(SortDirection.DESC, 0, 10);
    }

}
