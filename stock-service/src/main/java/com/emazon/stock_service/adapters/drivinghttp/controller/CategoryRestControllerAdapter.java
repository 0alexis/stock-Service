package com.emazon.stock_service.adapters.drivinghttp.controller;

import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddCategoryRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.UpdateCategoryRequest;

import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryResponseMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryResponseMapper categoryResponseMapper;
    private final ICategoryRequestMapper categoryRequestMapper;


    @PostMapping
    public ResponseEntity<Void> addCategory(@Valid @RequestBody AddCategoryRequest request) {
        categoryServicePort.saveCategory(categoryRequestMapper.addRequestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search/{categoryName}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String categoryName) {
        return ResponseEntity.ok(categoryResponseMapper.toCategoryResponse(categoryServicePort.getCategoryByName(categoryName)));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestParam Integer page, @RequestParam Integer size) {
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().build(); // O maneja el error de otra forma
        }
        return ResponseEntity.ok(categoryResponseMapper.toCategoryResponseList(categoryServicePort.getAllCategories(page, size)));
    }


    @PutMapping("/")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest request) {
        return ResponseEntity.ok(categoryResponseMapper.toCategoryResponse(
                categoryServicePort.updateCategory(categoryRequestMapper.updateRequestToCategory(request))
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryServicePort.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
