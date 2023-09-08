package com.example.be_project.model.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String source; // mã nguồn
    private String name;

    @Column(name = "created_by")
    private String createdBy; //username

    private String assignee; // Giao cho ai (username)
    private String link;
    private String note;

    private Date deadline;
    private String status;
    private String priority;

    @Column(name = "department_key")
    private String departmentKey;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

}
