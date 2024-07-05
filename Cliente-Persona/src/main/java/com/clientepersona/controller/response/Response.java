package com.clientepersona.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface Response<T> {

    int getStatusCode();
    void setStatusCode(int statusCode);

    void setMessage(String message);
    String getMessage();

    void setTimestamp(LocalDateTime time);
    LocalDateTime getTimestamp();

    T getPayload();
    void setPayload(T payload);

    /**
     *
     * @param statusCode
     * @param message
     * @param payload
     * @return
     */
    default ResponseEntity<Response<T>> buildResponseEntity(HttpStatus statusCode, String message, T payload) {
        this.setMessage(message);
        this.setTimestamp(LocalDateTime.now());
        this.setStatusCode(statusCode.value());
        this.setPayload(payload);
        return new ResponseEntity<>(this, statusCode);
    }

}
