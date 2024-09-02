package com.emazon.stock_service.adapters.drivend.jpa.mysql.exception;

    public class BrandAlreadyExistsException extends RuntimeException {
        public BrandAlreadyExistsException(String message) {
            super(message);
        }
    }

