package com.clientepersona.controller.response;

import java.time.LocalDateTime;

public class BaseResponse<T> implements Response<T> {
    private int statusCode;

    private LocalDateTime timestamp;

    private T payload;

    private String message;


    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setTimestamp(LocalDateTime time) {
        this.timestamp = time;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public T getPayload() {
        return payload;
    }

    @Override
    public void setPayload(T payload) {
        this.payload = payload;
    }
}
