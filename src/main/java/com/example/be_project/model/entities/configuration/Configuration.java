package com.example.be_project.model.entities.configuration;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private int type; //1-Tragj thái, 2-Công việc, 3-DV, 4-Hình thức, 5-Đối tượng, 6-Lý do fail

    @Column(name = "created_by")
    private String createdBy; //username

    private int days;
    private double hours;

    private int status;

    private String departmentKey;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}
