package com.ij.tdd.dto;

import lombok.Data;

@Data
public class ObjectResponse {

    private Object data;

    public ObjectResponse(Object data) {
        this.data = data;
    }

    public static ObjectResponse ok(Object data) {
        return new ObjectResponse(data);
    }
}
