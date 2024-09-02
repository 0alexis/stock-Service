package com.emazon.stock_service.adapters.drivinghttp.controller;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddBrandRequest;
import com.emazon.stock_service.adapters.drivinghttp.mapper.IBrandRequestMapper;
import com.emazon.stock_service.adapters.drivinghttp.mapper.IBrandResponseMapper;
import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BrandRestControllerAdapterTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;
    @Mock
    private IBrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandRestControllerAdapter brandRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCategory_WhenValidRequest_ShouldReturnCreatedStatus() {
        // Arrange
        AddBrandRequest request = new AddBrandRequest();
        Brand brand = new Brand(1L, "Electronics", "Electronic gadgets");

        when(brandRequestMapper.addRequestToBrand(request)).thenReturn(brand);

        // Act
        ResponseEntity<Void> response = brandRestControllerAdapter.addBrand(request);

        // Assert
        verify(brandServicePort, times(1)).saveBrand(brand);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    void addCategory_WhenCategoryAlreadyExists_ShouldThrowException() {
        // Arrange
        AddBrandRequest request = new AddBrandRequest();
        Brand brand = new Brand(1L, "Electronics", "Electronic gadgets");

        when(brandRequestMapper.addRequestToBrand(request)).thenReturn(brand);
        doThrow(new CategoryAlreadyExistsException("Brand already exists")).when(brandServicePort).saveBrand(any(Brand.class));

        // Act & Assert
        Exception exception = assertThrows(CategoryAlreadyExistsException.class, () -> {
            brandRestControllerAdapter.addBrand(request);
        });

        assertEquals("Brand already exists", exception.getMessage());
    }
}