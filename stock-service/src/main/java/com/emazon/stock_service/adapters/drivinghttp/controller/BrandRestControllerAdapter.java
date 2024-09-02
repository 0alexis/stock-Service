package com.emazon.stock_service.adapters.drivinghttp.controller;


import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddBrandRequest;
import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddCategoryRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
