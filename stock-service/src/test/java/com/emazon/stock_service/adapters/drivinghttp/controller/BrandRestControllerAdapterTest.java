package com.emazon.stock_service.adapters.drivinghttp.controller;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddBrandRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.SortDirectionRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.BrandPaginationResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.BrandResponse;
import com.emazon.stock_service.adapters.drivinghttp.mapper.IBrandRequestMapper;
import com.emazon.stock_service.adapters.drivinghttp.mapper.IBrandResponseMapper;
import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.model.Brand;
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
    @Test
    void getPaginationBrandsByAscAndDesc_ShouldReturnBrandPaginationResponse() {
        // Arrange
        SortDirectionRequest sortDirectionRequest = SortDirectionRequest.ASC;
        SortDirection sortDirection = SortDirection.ASC;
        int page = 0;
        int size = 10;

        List<Brand> brandList = List.of(new Brand(1L, "Electronics", "Devices"));
        CustomPage<Brand> customPage = new CustomPage<>(brandList, page, size, 1L, 1, true, true);
        BrandPaginationResponse<BrandResponse> paginationResponse = new BrandPaginationResponse<>(List.of(),
                0,
                10,
                1,
                1,
                true,
                true);

        when(brandRequestMapper.toSortDirection(sortDirectionRequest)).thenReturn(sortDirection);
        when(brandServicePort.getPaginationCategoriesByAscAndDesc(sortDirection, page, size)).thenReturn(customPage);
        when(brandResponseMapper.toBrandPaginationResponse(customPage)).thenReturn(paginationResponse);

        // Act
        BrandPaginationResponse<BrandResponse> response = brandRestControllerAdapter.getPaginationCategoriesByAscAndDesc(sortDirectionRequest, page, size);

        // Assert
        verify(brandServicePort, times(1)).getPaginationCategoriesByAscAndDesc(sortDirection, page, size);
        assertNotNull(response);
        assertEquals(paginationResponse, response);
        assertEquals(1, customPage.getTotalElements());
        assertEquals(1, customPage.getTotalPages());
        assertTrue(customPage.isFirst());
        assertTrue(customPage.isLast());
        assertEquals(brandList, customPage.getContent());
    }
    @Test
    void testAddBrand_whenBrandIsSavedSuccessfully_shouldReturnCreated() {
        // Arrange
        AddBrandRequest request = new AddBrandRequest();
        request.setName("New Brand");
        request.setDescription("Description for new brand");

        Brand brand = new Brand(null, request.getName(), request.getDescription());
        when(brandRequestMapper.addRequestToBrand(request)).thenReturn(brand);
        doNothing().when(brandServicePort).saveBrand(brand);

        // Act
        ResponseEntity<Void> response = brandRestControllerAdapter.addBrand(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(brandServicePort, times(1)).saveBrand(brand);
    }

}