package com.mcc5657.eventmanagement.model.response;

import java.util.List;

public class ResponseListData<E> {
    private List<E> data;
    private Long amount;

    public ResponseListData(List<E> data) {
        this.data = data;
    }

    public ResponseListData(List<E> data, Long amount) {
        this.data = data;
        this.amount = amount;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}