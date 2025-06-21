package com.mcc5657.eventmanagement.model;

public interface BasicCrudModel<E extends Object, I> {
    
    public I getId();

    public void setId(I id);
}