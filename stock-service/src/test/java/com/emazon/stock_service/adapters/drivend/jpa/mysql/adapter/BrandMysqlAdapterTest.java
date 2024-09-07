package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.BrandEntity;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.BrandAlreadyExistsException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.ElementNotFoundException;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.IBrandEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.IBrandRepository;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.CustomPage;
import com.emazon.stock_service.domain.model.SortDirection;
import com.emazon.stock_service.domain.util.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class BrandMysqlAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private IBrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandMysqlAdapter brandMysqlAdapter;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandMysqlAdapter = new BrandMysqlAdapter(brandRepository, brandEntityMapper);
    }

    @Test
    void getBrandById_ValidId_ShouldReturnCategory(){
        //arranque
        Long brandId = 1L;
        BrandEntity brandEntity = new BrandEntity(brandId, "brandname", "brandDescription");
        Brand brand = new Brand(brandId, "brandname", "brandDescription");

        //simulamos que se encuentra la marca por ID
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);
        //act
        Brand returned = brandMysqlAdapter.getBrandById(brandId);
        //assert
        assertNotNull(returned);  // Verificamos que el resultado no es null
        assertEquals(brandId, returned.getId());   // Verificamos que el ID coincide
        verify(brandRepository, times(1)).findById(brandId);  // Verificamos que se llamó a findById
        verify(brandEntityMapper).toBrand(brandEntity); // Verificamos que se mapeó correctamente
    }

    @Test
    void getCategoryById_InvalidId_ShouldReturnNull(){
        //arranque
        Long BrandID = 1L;

        //simulamos que no se encuentra ninguna categoria con ese iD
        when(brandRepository.findById(BrandID)).thenReturn(Optional.empty());

        //act
        Brand returned = brandMysqlAdapter.getBrandById(BrandID);

        // Assert
        assertNull(returned);  // Verificamos que el resultado es null
        verify(brandRepository).findById(BrandID);  // Verificamos que se llamó a findById
        verify(brandEntityMapper, never()).toBrand(any());  // Verificamos que no se llamó al mapeo
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
    @Test
    void getPaginationCategories_NoCategories_ShouldThrowElementNotFoundException() {
        // Arrange
        SortDirection sortDirection = SortDirection.ASC; // Configura la dirección de ordenación
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name"))); // Configura el Pageable
        Page<BrandEntity> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0); // Crea una página vacía

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(emptyPage); // Simula el retorno de una página vacía

        // Act & Assert
        assertThrows(ElementNotFoundException.class, () -> {
            brandMysqlAdapter.getPaginationBrands(sortDirection, 0, 10);
        });
    }
    @Test
    void getPaginationCategories_ShouldReturnCustomPage() {
        // Arrange
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Devices");
        List<BrandEntity> brandEntities = List.of(brandEntity);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name")));
        Page<BrandEntity> bransPage = new PageImpl<>(brandEntities, pageable, brandEntities.size());

        when(brandRepository.findAll(pageable)).thenReturn(bransPage);

        // Act
        CustomPage<Brand> result = brandMysqlAdapter.getPaginationBrands(SortDirection.ASC, 0, 10);

        // Assert
        assertEquals(0, bransPage.getNumber());
        assertEquals(10, bransPage.getSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertEquals(1, result.getContent().size());

    }
    @Test
    void getPaginationCategories_ShouldThrowElementNotFoundException_WhenNoCategoriesFound() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name")));
        Page<BrandEntity> bransPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(bransPage);

        // Act & Assert
        assertThrows(ElementNotFoundException.class, () -> {
            brandMysqlAdapter.getPaginationBrands(SortDirection.ASC, 0, 10);
        });
    }
    @Test
    void getPaginationCategories_ShouldReturnCustomPage_WhenSortedDescending() {
        // Arrange
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Devices");
        List<BrandEntity> brandEntities = List.of(brandEntity);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("name")));
        Page<BrandEntity> brandspage = new PageImpl<>(brandEntities, pageable, brandEntities.size());

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandspage);

        // Act
        CustomPage<Brand> result =  brandMysqlAdapter.getPaginationBrands(SortDirection.ASC, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertEquals("Electronics", result.getContent().get(0).getName());
    }
    @Test
    void testCategoryConstructor_WithNullDescription_ShouldThrowException() {
        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Category(1L, "Gadgets", null);
        });

        assertEquals(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE, exception.getMessage());
    }
    //    @Test
//    void testCategoryConstructor_WithEmptyName_ShouldThrowException() {
//        // Act & Assert
//        Exception exception = assertThrows(EmptyFieldException.class, () -> {
//            new Category(1L, "", "Gadgets description");
//        });
//
//        assertEquals(DomainConstants.Field.NAME.toString(), exception.getMessage());
//    }
//    @Test
//    void testCategoryConstructor_WithNameContainingOnlySpaces_ShouldThrowException() {
//        // Act & Assert
//        Exception exception = assertThrows(EmptyFieldException.class, () -> {
//            new Category(1L, "   ", "Gadgets description");
//        });
//
//        assertEquals(DomainConstants.Field.NAME.toString(), exception.getMessage());
//    }
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