package com.example.be_project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletDTO {
    private String name;
    //private int departmentId;
    private int positionId;
    private String walletName;
    private BigDecimal amount;
}
