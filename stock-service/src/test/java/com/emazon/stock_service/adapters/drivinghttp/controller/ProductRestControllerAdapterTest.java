package com.emazon.stock_service.adapters.drivinghttp.controller;

import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddProductRequest;
import com.emazon.stock_service.adapters.drivinghttp.mapper.IProductRequestMapper;
import com.emazon.stock_service.domain.api.IProductServicePort;
import com.emazon.stock_service.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class ProductRestControllerAdapterTest {
    @Mock
    private IProductServicePort productServicePort;
    @Mock
    private IProductRequestMapper productRequestMapper;


    @InjectMocks
    private ProductRestControllerAdapter productRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void addProduct_WhenValidRequest_ShouldReturnCreatedStatus() {
        AddProductRequest request = new AddProductRequest();
        Product product = new Product();

        when(productRequestMapper.addRequestToProduct(request)).thenReturn(product);
        // Act
        ResponseEntity<Void> response = productRestControllerAdapter.addProduct(request);
        // Assert
        verify(productServicePort, times(1)).saveProduct(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


}