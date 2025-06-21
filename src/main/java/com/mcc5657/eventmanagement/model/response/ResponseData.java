package com.mcc5657.eventmanagement.model.response;

public class ResponseData<E> {
    private E data;

    public ResponseData(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}