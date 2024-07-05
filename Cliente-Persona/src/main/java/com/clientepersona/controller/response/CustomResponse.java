package com.clientepersona.controller.response;


public class CustomResponse<T> {

    T getPayload() {
        return null;
    }

    void setPayload(T payload) {

    }


   public CustomResponse<T> buildResponseEntity(T payload) {

        this.setPayload(payload);
        return this;
    }
}
