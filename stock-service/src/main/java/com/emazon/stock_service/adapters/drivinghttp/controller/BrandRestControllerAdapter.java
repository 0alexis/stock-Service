package com.emazon.stock_service.adapters.drivinghttp.controller;


import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddBrandRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddCategoryRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.SortDirectionRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.BrandPaginationResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.BrandResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryPaginationResponse;
import com.emazon.stock_service.adapters.drivinghttp.dto.response.CategoryResponse;
import com.emazon.stock_service.adapters.drivinghttp.mapper.IBrandRequestMapper;
import com.emazon.stock_service.adapters.drivinghttp.mapper.IBrandResponseMapper;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryRequestMapper;
import com.emazon.stock_service.adapters.drivinghttp.mapper.ICategoryResponseMapper;
import com.emazon.stock_service.domain.api.IBrandServicePort;
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
@RequestMapping("/brand")

public class BrandRestControllerAdapter {

    private final IBrandServicePort brandServicePort;
    private final IBrandResponseMapper brandResponseMapper;
    private final IBrandRequestMapper brandRequestMapper;


    @Operation(summary = "Create a new brand",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Brand created",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Brand already exists",
                            content = @Content(mediaType = "application/json"))
            })

    @PostMapping("/")
    public ResponseEntity<Void> addBrand(@Valid @RequestBody AddBrandRequest request) {
        brandServicePort.saveBrand(brandRequestMapper.addRequestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Brand Pagination",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagination Brand successful",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Error in Pagination Brand",
                            content = @Content(mediaType = "application/json"))})
    @GetMapping("/pagination")
    public BrandPaginationResponse<BrandResponse> getPaginationCategoriesByAscAndDesc(@Valid
            @RequestParam(defaultValue = "ASC") SortDirectionRequest sortDirectionRequest,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return brandResponseMapper
                .toBrandPaginationResponse(brandServicePort
                        .getPaginationCategoriesByAscAndDesc(brandRequestMapper
                                .toSortDirection(sortDirectionRequest), page, size));
    }
}
