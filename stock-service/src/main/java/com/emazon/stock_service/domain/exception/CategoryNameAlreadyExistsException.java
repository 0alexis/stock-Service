package com.emazon.stock_service.domain.exception;


public class CategoryNameAlreadyExistsException extends IllegalArgumentException{
    public CategoryNameAlreadyExistsException(String message) { super(message); };
}
