package com.emazon.stock_service.adapters.drivinghttp.controller;


import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddCategoryRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.SortDirectionRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryPaginationResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryResponseMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CategoryRestControllerAdapterTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;
    @Mock
    private ICategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryRestControllerAdapter categoryRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
       // categoryRestControllerAdapter = new CategoryRestControllerAdapter(categoryServicePort, categoryRequestMapper);
    }

    @Test
    void addCategory_WhenValidRequest_ShouldReturnCreatedStatus() {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        Category category = new Category(1L, "Electronics", "Electronic gadgets");

        when(categoryRequestMapper.addRequestToCategory(request)).thenReturn(category);

        // Act
        ResponseEntity<Void> response = categoryRestControllerAdapter.addCategory(request);

        // Assert
        verify(categoryServicePort, times(1)).saveCategory(category);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    void addCategory_WhenCategoryAlreadyExists_ShouldThrowException() {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        Category category = new Category(1L, "Electronics", "Electronic gadgets");

        when(categoryRequestMapper.addRequestToCategory(request)).thenReturn(category);
        doThrow(new CategoryAlreadyExistsException("Category already exists")).when(categoryServicePort).saveCategory(any(Category.class));

        // Act & Assert
        Exception exception = assertThrows(CategoryAlreadyExistsException.class, () -> {
            categoryRestControllerAdapter.addCategory(request);
        });

        assertEquals("Category already exists", exception.getMessage());
    }
    @Test
    void getPaginationCategoriesByAscAndDesc_ShouldReturnCategoryPaginationResponse() {
        // Arrange
        SortDirectionRequest sortDirectionRequest = SortDirectionRequest.ASC;
        SortDirection sortDirection = SortDirection.ASC;
        int page = 0;
        int size = 10;

        List<Category> categoryList = List.of(new Category(1L, "Electronics", "Devices"));
        CustomPage<Category> customPage = new CustomPage<>(categoryList, page, size, 1L, 1, true, true);
        CategoryPaginationResponse<CategoryResponse> paginationResponse = new CategoryPaginationResponse<>(List.of(),
                0,
                10,
                1,
                1,
                true,
                true);

        when(categoryRequestMapper.toSortDirection(sortDirectionRequest)).thenReturn(sortDirection);
        when(categoryServicePort.getPaginationCategoriesByAscAndDesc(sortDirection, page, size)).thenReturn(customPage);
        when(categoryResponseMapper.toCategoryPaginationResponse(customPage)).thenReturn(paginationResponse);

        // Act
        CategoryPaginationResponse<CategoryResponse> response = categoryRestControllerAdapter.getPaginationCategoriesByAscAndDesc(sortDirectionRequest, page, size);

        // Assert
        verify(categoryServicePort, times(1)).getPaginationCategoriesByAscAndDesc(sortDirection, page, size);
        assertNotNull(response);
        assertEquals(paginationResponse, response);
        assertEquals(1, customPage.getTotalElements());
        assertEquals(1, customPage.getTotalPages());
        assertTrue(customPage.isFirst());
        assertTrue(customPage.isLast());
        assertEquals(categoryList, customPage.getContent());
    }
    @Test
    void testAddCategory_whenCategoryIsSavedSuccessfully_shouldReturnCreated() {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        request.setName("NewCategory");
        request.setDescription("Description for new category");

        Category category = new Category(null, request.getName(), request.getDescription());
        when(categoryRequestMapper.addRequestToCategory(request)).thenReturn(category);
        doNothing().when(categoryServicePort).saveCategory(category);

        // Act
        ResponseEntity<Void> response = categoryRestControllerAdapter.addCategory(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(categoryServicePort, times(1)).saveCategory(category);
    }

}