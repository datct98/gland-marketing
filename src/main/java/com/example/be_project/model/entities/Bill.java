package com.example.be_project.model.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bill {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    //String name;
    long userId;
    @Column(name = "bill_config_id")
    long billConfigId;

    @Column(name = "store_id")
    long storeId;
    @Column(name = "pay_person")
    String payPerson; // người nộp or người nhận
    BigDecimal amount;
    String status; // Thành công - Hủy - Chờ duyệt
    String note;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}
