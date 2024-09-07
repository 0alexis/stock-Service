package com.emazon.stock_service.adapters.drivinghttp.controller;

import com.emazon.stock_service.adapters.drivinghttp.dto.request.AddProductRequest;
import com.emazon.stock_service.adapters.drivinghttp.mapper.IProductRequestMapper;
import com.emazon.stock_service.domain.api.IProductServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product")

public class ProductRestControllerAdapter {

    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

    @PostMapping("/")
    @Operation(summary = "Create a new Product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Product already exists",
                            content = @Content(mediaType = "application/json"))
            })

    public ResponseEntity<Void> addProduct (@Valid @RequestBody AddProductRequest request) {
        productServicePort.saveProduct(productRequestMapper.addRequestToProduct(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
