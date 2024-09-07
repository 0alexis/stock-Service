package com.emazon.stock_service.domain.usecase;


import com.emazon.stock_service.domain.exception.ProductCannotHaveMoreThanThreeCategoriesException;
import com.emazon.stock_service.domain.exception.ProductCategoryRepeatedException;
import com.emazon.stock_service.domain.exception.ProductMustHaveAtLeastOneCategoryException;
import com.emazon.stock_service.domain.model.Brand;
import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.model.Product;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.spi.IProductPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductSuccess() {
        // Arrange
        // Creamos un producto de prueba
        Category category = new Category(1L, "CategoryName", "CategoryDescription");
        Brand brand = new Brand(1L, "BrandName", "BrandDescription");
        Product product = new Product(
                1L,
                "productName",
                "ProductDescription",
                10,
                100.0,
                1L,       // BrandId
                List.of(1L)  // CategoryId
        );
        // Simulamos los comportamientos de las dependencias
      when(brandPersistencePort.getBrandById(1L)).thenReturn(brand);

      when(categoryPersistencePort.getCategoryById(1L)).thenReturn(category);

      /* no se haga nada, es decir, no se ejecute ninguna lógica*/
        doNothing().when(productPersistencePort).saveProduct(any(Product.class));
        // Act
        productPersistencePort.saveProduct(product);
        // Assert
        verify(productPersistencePort).saveProduct(product); // Verificamos que se haya llamado
    }
    @Test
     void testGetCategoryByIds_NullOrEmpty_ShouldThrowException(){
        // Test con lista vacía
        Product product = new Product();
        product.setCategoryId(List.of());

        assertThrows(ProductMustHaveAtLeastOneCategoryException.class, () ->
                productUseCase.saveProduct(product));

    }
    @Test
     void testGetCategoryByIds_MoreThanThree_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setCategoryId(List.of(1L, 2L, 3L, 4L)); // More than three categories

        // Act & Assert
        assertThrows(ProductCannotHaveMoreThanThreeCategoriesException.class, () ->
            productUseCase.saveProduct(product));

    }
    @Test
     void testGetCategoryByIds_DuplicatedCategories_ShouldThrowException(){
        Product product = new Product();
        product.setCategoryId(List.of(1L, 2L, 2L)); //duplicatedCategoryIds
    assertThrows(ProductCategoryRepeatedException.class, () -> productUseCase.saveProduct(product));
    }

    @Test
     void testGetCategoryByIds_ValidInput_ShouldReturnCategories() {
        // Arrange
        Product product = new Product();
        product.setCategoryId(List.of(1L, 2L, 3L));

        Category category1 = new Category(1L, "Category1", "Description1");
        Category category2 = new Category(2L, "Category2", "Description2");
        Category category3 = new Category(3L, "Category3", "Description3");

        // Simulamos el comportamiento de las dependencias
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(category1);
        when(categoryPersistencePort.getCategoryById(2L)).thenReturn(category2);
        when(categoryPersistencePort.getCategoryById(3L)).thenReturn(category3);

        // Act
        Set<Category> categories = product.getCategoryId()
                .stream()
                .map(categoryPersistencePort::getCategoryById)
                .collect(Collectors.toSet());

        // Assert
        assertEquals(3, categories.size());
        assertTrue(categories.contains(category1));
        assertTrue(categories.contains(category2));
        assertTrue(categories.contains(category3));

        // Verificar que se llamaron las dependencias correctas
        verify(categoryPersistencePort).getCategoryById(1L);
        verify(categoryPersistencePort).getCategoryById(2L);
        verify(categoryPersistencePort).getCategoryById(3L);
    }


    @Test
    void saveProduct_InvalidCategories_ShouldThrowException() {
        // Arrange
        Product product = new Product();
        product.setCategoryId(List.of()); // Sin categorías, lo que debería lanzar una excepción

        // Act & Assert
        assertThrows(ProductMustHaveAtLeastOneCategoryException.class, () ->
            productUseCase.saveProduct(product));


        verify(productPersistencePort, never()).saveProduct(product); // Verificamos que no se llamó a la persistencia
    }

    @Test
    void getCategoryByIds_ValidIds_ShouldReturnCategories() {
        // Arrange
        List<Long> categoryIds = List.of(1L, 2L);
        Brand brand = new Brand(1L, "BrandName","BrandDescription");

        Product product = new Product();
        product.setCategoryId(categoryIds);
        product.setBrandId(1L);


        // Simulamos la obtención de las categorías desde el puerto de persistencia
        Category category1 = new Category(1L, "Category1", "Description1");
        Category category2 = new Category(2L, "Category2", "Description2");

        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(category1);
        when(categoryPersistencePort.getCategoryById(2L)).thenReturn(category2);
        when(brandPersistencePort.getBrandById(1L)).thenReturn(brand);


        // Act
        productUseCase.saveProduct(product);
        // Assert
        Set<Category> expectedCategories = Stream.of(category1, category2).collect(Collectors.toSet());
        assertEquals(expectedCategories, product.getCategories());
        assertEquals(brand, product.getBrand());

        // Verificamos que se llamaron correctamente los métodos del puerto de persistencia
        verify(productPersistencePort).saveProduct(product);

    }


}