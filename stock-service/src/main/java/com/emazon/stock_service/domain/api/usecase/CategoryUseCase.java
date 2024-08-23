package com.emazon.stock_service.domain.api.usecase;

import com.emazon.stock_service.domain.model.Category;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/categories")

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @PostMapping
    @Operation(summary = "Create a new category", responses = {
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "400", description = "Category already exists")
    })
    @Override
    public void saveCategory(@RequestBody Category category) {
        categoryPersistencePort.saveCategory(category);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get a category by name", responses = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @Override
    public Category getCategoryByName(@PathVariable String name) {
        return categoryPersistencePort.getCategoryByName(name);
    }

    @GetMapping
    @Operation(summary = "Get all categories with pagination")
    @Override
    public List<Category> getAllCategories(@RequestParam  Integer page, @RequestParam  Integer size) {
        return categoryPersistencePort.getAllCategories(page, size);
    }

    @PutMapping
    @Operation(summary = "Update an existing category", responses = {
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @Override
    public Category updateCategory(@RequestBody Category category) {
        return categoryPersistencePort.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by id", responses = {
            @ApiResponse(responseCode = "204", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @Override
    public void deleteCategory(@PathVariable Long id) {
        categoryPersistencePort.deleteCategory(id);
    }
}






