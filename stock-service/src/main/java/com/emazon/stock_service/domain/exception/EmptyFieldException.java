package com.emazon.stock_service.domain.exception;


public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String message) {
        super(message);
    }
}

