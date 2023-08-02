package com.example.be_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> {
    private int status;
    private String message;
    private T value;

    public DataResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
