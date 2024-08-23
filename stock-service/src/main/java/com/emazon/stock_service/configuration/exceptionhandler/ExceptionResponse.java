package com.emazon.stock_service.configuration.exceptionhandler;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private final String message;
    private final String status;
    private LocalDateTime now;

    public ExceptionResponse(String message, String status, LocalDateTime now){
        this.message = message;
        this.status = status;
        this.now = now;

    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }
}
