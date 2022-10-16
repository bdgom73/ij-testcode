package com.ij.tdd.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    private int status;
    private String message;

    private ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ApiResponse ok() {
        return new ApiResponse(200, "ok");
    }
}
