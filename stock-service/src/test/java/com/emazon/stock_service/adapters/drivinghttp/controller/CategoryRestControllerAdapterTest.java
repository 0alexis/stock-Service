package com.emazon.stock_service.adapters.drivinghttp.controller;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddCategoryRequest;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.model.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryRestControllerAdapterTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @InjectMocks
    private CategoryRestControllerAdapter categoryRestControllerAdapter;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestControllerAdapter).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddCategory_whenCategoryIsSavedSuccessfully_shouldReturnCreated() throws Exception {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        request.setName("NewCategory");
        request.setDescription("Description for new category");

        // Act & Assert
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(categoryServicePort).saveCategory(any());
    }


    @Test
    void testSaveCategory_whenNameIsBlank_shouldReturnBadRequest() throws Exception {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        request.setName("");
        request.setDescription("Valid description");

        // Act & Assert
        mockMvc.perform(post("/category")
                        .flashAttr("addCategoryRequest", request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testSaveCategory_whenDescriptionIsBlank_shouldReturnBadRequest() throws Exception {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        request.setName("Yeison");
        request.setDescription("");

        // Act & Assert
        mockMvc.perform(post("/category")
                        .flashAttr("addCategoryRequest", request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testSaveCategory_whenNameIsNull_shouldReturnBadRequest() throws Exception {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        request.setDescription("Valid description");

        // Act & Assert
        mockMvc.perform(post("/category")
                        .flashAttr("addCategoryRequest", request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testSaveCategory_whenDescriptionIsNull_shouldReturnBadRequest() throws Exception {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        request.setName("Alexis");

        // Act & Assert
        mockMvc.perform(post("/category")
                        .flashAttr("addCategoryRequest", request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testSaveCategory_whenNameExceedsMaxLength_shouldReturnBadRequest() throws Exception {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        request.setName("A".repeat(51));
        request.setDescription("Valid description");

        // Act & Assert
        mockMvc.perform(post("/category")
                        .flashAttr("addCategoryRequest", request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testSaveCategory_whenDescriptionExceedsMaxLength_shouldReturnBadRequest() throws Exception {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest();
        request.setName("Alexis");
        request.setDescription("G".repeat(91));

        // Act & Assert
        mockMvc.perform(post("/category")
                        .flashAttr("addCategoryRequest", request))
                .andExpect(status().isBadRequest());
    }



}