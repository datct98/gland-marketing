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
    private int totalPage;

    public DataResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public DataResponse(T value, int totalPage) {
        this.value = value;
        this.totalPage = totalPage;
    }

    public DataResponse(T value) {
        this.value = value;
    }
}
