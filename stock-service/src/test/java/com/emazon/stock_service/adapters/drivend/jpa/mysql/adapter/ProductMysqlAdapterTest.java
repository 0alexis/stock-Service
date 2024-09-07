package com.emazon.stock_service.adapters.drivend.jpa.mysql.adapter;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.ProductEntity;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.stock_service.adapters.drivend.jpa.mysql.repository.IProductRepository;
import com.emazon.stock_service.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductMysqlAdapterTest {

    @Mock
    private  IProductRepository productRepository;
    @Mock
    private  IProductEntityMapper productEntityMapper;

    @InjectMocks
    private ProductMysqlAdapter productMysqlAdapter;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
       // productMysqlAdapter = new ProductMysqlAdapter(productRepository, productEntityMapper);
    }


    @Test
    void saveProduct_WhenCategoryDoesNotExist() {
        //arrenque
         Product product = new Product(
                1L,
                "productName",
                "ProductDescription",
                10,
                100.0,
                1L,       // BrandId
                List.of(1L)  // CategoryId
        );
        ProductEntity productEntity = new ProductEntity(
                1L,
                "productName",
                "ProductDescription",
                10,
                100.0,
                null,
                null
        );
        //simulamos convertir producto a product entity
        when(productEntityMapper.toProductEntity(product)).thenReturn(productEntity);

        //Act
        productMysqlAdapter.saveProduct(product);

        // Assert
        // Verificamos que se llamó el método de mapeo y que se guardó el producto en el repositorio
        verify(productEntityMapper).toProductEntity(product);
        verify(productRepository).save(productEntity);
    }
    @Test
    void saveProduct_EmptyProduct_ShouldSaveProductEntity() {
        //arranque
        Product product = new Product();
        ProductEntity productEntity = new ProductEntity();

        // Simulamos la conversión de Product vacío a ProductEntity vacío
        when(productEntityMapper.toProductEntity(product)).thenReturn(productEntity);

        // Act
        productMysqlAdapter.saveProduct(product);

        // Assert
        // Verificamos que el producto vacío fue mapeado y guardado correctamente

        verify(productRepository).save(productEntity);
        verify(productRepository).save(productEntity);
    }

    @Test
    void saveProduct_NullProduct_ShouldSaveProductEntity() {
        //arranque
        Product product = new Product(
                1L,
                null,
                null,
                null,
                null,
                null,
                List.of()
        );
        ProductEntity productEntity = new ProductEntity(
                1L,
                null,
                null,
                null,
                100.0,
                null,
                null
        );
        // Simulamos la conversión de un producto con valores nulos a una entidad con valores nulos

        when(productEntityMapper.toProductEntity(product)).thenReturn(productEntity);

        // Act
        productMysqlAdapter.saveProduct(product);

        // Assert
        // Verificamos que el producto con valores nulos fue mapeado y guardado correctamente
        verify(productEntityMapper).toProductEntity(product);
        verify(productRepository).save(productEntity);


    }
}