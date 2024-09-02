package com.emazon.stock_service.adapters.drivinghttp.controller;

import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddCategoryRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.SortDirectionRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryPaginationResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryResponseMapper;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/category")

public class CategoryRestControllerAdapter {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryResponseMapper categoryResponseMapper;
    private final ICategoryRequestMapper categoryRequestMapper;


@Operation(summary = "Create a new category",
        responses = {
                @ApiResponse(responseCode = "201", description = "Category created",
                        content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "409", description = "Category already exists",
                        content = @Content(mediaType = "application/json"))
        })

@PostMapping("/")
    public ResponseEntity<Void> addCategory(@Valid @RequestBody AddCategoryRequest request) {
        categoryServicePort.saveCategory(categoryRequestMapper.addRequestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Category Pagination",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagination Category successful",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Error in Pagination Category",
                            content = @Content(mediaType = "application/json"))})
    @GetMapping("/pagination")
    public CategoryPaginationResponse<CategoryResponse> getPaginationCategoriesByAscAndDesc(@Valid
        @RequestParam(defaultValue = "ASC") SortDirectionRequest sortDirectionRequest,
          @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return categoryResponseMapper
                .toCategoryPaginationResponse(categoryServicePort
                        .getPaginationCategoriesByAscAndDesc(categoryRequestMapper
                                .toSortDirection(sortDirectionRequest), page, size));
    }

}
