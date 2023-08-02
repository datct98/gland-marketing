package com.example.be_project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBillDTO {
    private long billId;
    private String billConfigName; //Loaij thu, Loại chi
    private boolean type; // Phiếu thu - chi
    private boolean billConfigStatus; //Hoạt động - k hđ
    private String creator; // Người tạo
    private Date createdAt;
    private String statusBill; //Đã duyệt - đã hủy - chờ duyệt
    private BigDecimal amount;
    private String nameWallet;
    private String nameDepartment;
    private String note;
    private String payPerson; // Người nộp, người chi
    private long billConfigId;
    private int departmentId;
    private long storeId;

    public UserBillDTO(String billConfigName, boolean type, boolean billStatus, String creator, Date createdAt, long billConfigId) {
        this.billConfigName = billConfigName;
        this.type = type;
        this.billConfigStatus = billStatus;
        this.creator = creator;
        this.createdAt = createdAt;
        this.billConfigId = billConfigId;
    }

    public UserBillDTO(Date createdAt, String creator, String payPerson, String billConfigName, BigDecimal amount, String nameDepartment, String note, long billId) {
        this.billConfigName = billConfigName;
        this.creator = creator;
        this.createdAt = createdAt;
        this.amount = amount;
        this.nameDepartment = nameDepartment;
        this.note = note;
        this.payPerson = payPerson;
        this.billId = billId;
    }
}
