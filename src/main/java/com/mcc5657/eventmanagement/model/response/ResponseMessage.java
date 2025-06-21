package com.mcc5657.eventmanagement.model.response;

public class ResponseMessage<E> extends ResponseData<E> {
    private String message;

    public ResponseMessage(E data, String message) {
        super(data);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}