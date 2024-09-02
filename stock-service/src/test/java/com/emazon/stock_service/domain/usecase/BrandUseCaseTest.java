package com.emazon.stock_service.domain.usecase;

import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        brand.setDescription("Description for existing category");

        doThrow(new RuntimeException("A brand with this name already exists."))
                .when(brandPersistencePort).saveBrand(any(Brand.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        // Verifica el mensaje de la excepción
        assertEquals("A brand with this name already exists.", exception.getMessage());
    }
}