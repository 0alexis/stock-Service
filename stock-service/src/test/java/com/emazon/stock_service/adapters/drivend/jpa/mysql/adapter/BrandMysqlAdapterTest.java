package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.BrandEntity;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.BrandAlreadyExistsException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.IBrandRepository;
import com.emazon.stock_service.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class BrandMysqlAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private IBrandEntityMapper brandEntityMapper; ;

    @InjectMocks
    private BrandMysqlAdapter brandMysqlAdapter;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandMysqlAdapter = new BrandMysqlAdapter(brandRepository, brandEntityMapper);
    }

    @Test
    void saveBrand_ShouldSaveBrandSuccessfully_WhenBrandDoesNotExist() {
        // Arrange
        Brand brand = new Brand(1L, "Electronics", "Electronic gadgets");
        brand.setName("Electronics");
        brand.setDescription("Devices");
        BrandEntity brandEntity = new BrandEntity();
        when(brandEntityMapper.toBrandEntity(brand)).thenReturn(brandEntity);

        // Act
        brandMysqlAdapter.saveBrand(brand);

        // Assert
        verify(brandRepository, times(1)).save(brandEntity);
    }
    @Test
    void findByName_BrandExists_ShouldReturnBrand() {
        // Arrange
        String brandName = "Electronics";
        Brand brand = new Brand(1L, "Electronics", "Electronic gadgets");
        brand.setName(brandName);
        brand.setDescription("Devices");
        BrandEntity brandEntity = new BrandEntity();
        when(brandRepository.findByName(brandName)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        // Act
        Brand result = brandMysqlAdapter.findByName(brandName);

        // Assert
        assertEquals(brand, result);
    }
    @Test
    void saveCategory_ShouldHandleException() {
        //Arrange
        Brand brand = new Brand(1L, "Electronics", "Electronic gadgets");
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Electronic gadgets");
        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.of(brandEntity) );

        //ACT
        assertThrows(BrandAlreadyExistsException.class, () ->
                brandMysqlAdapter.saveBrand(brand)
        );
    }
}