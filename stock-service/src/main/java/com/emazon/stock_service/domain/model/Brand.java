package com.emazon.stock_service.domain.model;

import com.emazon.stock_service.domain.exception.BrandNameAlreadyExistsException;
import com.emazon.stock_service.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Brand {

    private  Long id;
    private  String name;
    private  String description;


    public Brand(Long id, String name, String description){
        if(name.trim().isEmpty()){
            throw new BrandNameAlreadyExistsException(DomainConstants.Field.NAME.toString());
        }
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_BRAND_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}



