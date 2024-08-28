package com.emazon.stock_service.domain.model;


import com.emazon.stock_service.domain.exception.EmptyFieldException;
import com.emazon.stock_service.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;


public class Category {
    private  Long id;
    private  String name;
    private  String description;

    public Category(Long id, String name, String description){
        if(name.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);

    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
