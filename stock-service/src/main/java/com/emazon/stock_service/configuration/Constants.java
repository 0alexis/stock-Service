package com.emazon.stock_service.configuration;

public class Constants {
    private Constants(){
        throw new IllegalStateException("utility class");
    }

    public static final String NO_DATA_FOUND_EXCEPTION_MESSAGE = "No data was found in the database";
    public static final String ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE = "The element indicated does not exist";
    public static final String PRODUCT_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The product you want to create already exists";
    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String NEGATIVE_NOT_ALLOWED_EXCEPTION_MESSAGE = "Field %s can not receive negative values";
    //   public static final String RESPONSE_ERROR_MESSAGE = "Error:";
//    public static final String SWAGGER_TITLE_MESSAGE = "Stock API Pragma Power Up Full Stack";
//    public static final String SWAGGER_DESCRIPTION_MESSAGE = "Stock microservice";
//    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
//    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
//    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
//    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}

