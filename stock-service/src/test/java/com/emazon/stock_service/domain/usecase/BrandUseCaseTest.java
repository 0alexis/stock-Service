package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategorySuccess() {
        // Arrange
        Brand brand = new Brand(1L, "Brand", "Description");
        brand.setName("Valid Name");
        brand.setDescription("Valid Description");
        when(brandPersistencePort.findByName(brand.getName())).thenReturn(null);

        // Act & Assert
        assertDoesNotThrow(() -> brandUseCase.saveBrand(brand));
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }
    @Test
    void testSaveBrand_BrandAlreadyExists() {
        //GIVEN
        Brand brand = new Brand(1L, "Books", "Varius books");

        brandPersistencePort.saveBrand(brand);
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }
    @Test
    void testSaveBrand_whenPersistenceFails_shouldThrowException() {
        // Arrange
        Brand brand = new Brand(1L, "Brand", "Various toys");

        doThrow(new RuntimeException("Database connection error"))
                .when(brandPersistencePort).saveBrand(any(Brand.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals("Database connection error", exception.getMessage());
    }
    @Test
    void testSaveBrand_whenNameAlreadyExists_shouldThrowException() {
        // Arrange
        Brand brand = new Brand(1L, "Alexis","Alexis´s description");
        brand.setName("ExistingBrand");
        brand.setDescription("Description for existing brand");

        doThrow(new RuntimeException("A brand with this name already exists."))
                .when(brandPersistencePort).saveBrand(any(Brand.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        // Verifica el mensaje de la excepción
        assertEquals("A brand with this name already exists.", exception.getMessage());
    }
    @Test
    void testSaveCategory_whenPersistencePortThrowsException_shouldPropagateException() {
        // Arrange
        Brand brand = new Brand(1L, "ValidName", "Valid description");

        doThrow(new RuntimeException("Unexpected error")).when(brandPersistencePort).saveBrand(any(Brand.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals("Unexpected error", exception.getMessage());
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }
    @Test
    void testGetPaginationCategoriesByAscAndDesc_whenSortDirectionIsAsc_shouldReturnSortedCategoriesAscending() {
        // Arrange
        Brand brand1 = new Brand(1L, "ASUS", "Various books");
        Brand brand2 = new Brand(2L, "Electronics", "All kinds of electronics");
        Brand brand3 = new Brand(3L, "Toys", "Various toys");

        List<Brand> brandList = Arrays.asList(brand1, brand2, brand3);
        CustomPage<Brand> customPage = new CustomPage<>(brandList, 0, 10, 3L, 1, true, true);

        when(brandPersistencePort.getPaginationBrands(SortDirection.ASC, 0, 10)).thenReturn(customPage);

        // Act
        CustomPage<Brand> result = brandUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.ASC, 0, 10);

        // Assert
        List<Brand> sortedBrands = result.getContent();
        assertEquals(3, sortedBrands.size());
        assertEquals("ASUS", sortedBrands.get(0).getName());
        assertEquals("Electronics", sortedBrands.get(1).getName());
        assertEquals("Toys", sortedBrands.get(2).getName());

        verify(brandPersistencePort, times(1)).getPaginationBrands(SortDirection.ASC, 0, 10);
    }
    @Test
    void testGetPaginationCategoriesByAscAndDesc_whenSortDirectionIsDesc_shouldReturnSortedCategoriesDescending() {
        // Arrange
        Brand brand1 = new Brand(1L, "Books", "Various books");
        Brand brand2 = new Brand(2L, "Electronics", "All kinds of electronics");
        Brand brand3 = new Brand(3L, "Toys", "Various toys");

        List<Brand> brandList = Arrays.asList(brand1, brand2, brand3);
        CustomPage<Brand> customPage = new CustomPage<>(brandList, 0, 10, 3L, 1, true, true);

        when(brandPersistencePort.getPaginationBrands(SortDirection.DESC, 0, 10)).thenReturn(customPage);

        // Act
        CustomPage<Brand> result = brandUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.DESC, 0, 10);

        // Assert
        List<Brand> sortedBrands = result.getContent();
        assertEquals(3, sortedBrands.size());
        assertEquals("Toys", sortedBrands.get(0).getName());
        assertEquals("Electronics", sortedBrands.get(1).getName());
        assertEquals("Books", sortedBrands.get(2).getName());

        verify(brandPersistencePort, times(1)).getPaginationBrands(SortDirection.DESC, 0, 10);
    }
}