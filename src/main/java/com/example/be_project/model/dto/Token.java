package com.example.be_project.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Token {
    private String accessToken;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }
}
