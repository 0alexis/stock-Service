package com.emazon.stock_service.domain.model;


import com.emazon.stock_service.domain.exception.EmptyFieldException;
import com.emazon.stock_service.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Category {
    private final long id;
    private final String name;
    private final String description;

    public Category(long id, String name, String description){
        if(name.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
