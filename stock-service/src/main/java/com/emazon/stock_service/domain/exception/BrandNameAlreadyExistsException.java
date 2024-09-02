package com.emazon.stock_service.domain.exception;

public class BrandNameAlreadyExistsException extends IllegalArgumentException{
    public BrandNameAlreadyExistsException(String message) {
        super(message);
    }
}
