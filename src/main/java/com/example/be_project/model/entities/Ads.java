package com.example.be_project.model.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String source; // mã nguồn
    @Column(name = "person_id")
    private long personId;

    @Column(name = "service_id")
    private long serviceId;

    @Column(name = "form_id")
    private long formId;

    @Column(name = "work_id")
    private long workId;

    @Column(name = "status_id")
    private long statusId;

    @Column(name = "created_by")
    private long createdBy; //userId

    private String assignee; // Giao cho ai (username)
    private String link;
    private String note;

    @Column(name = "store_id")
    private long storeId;

    private Date deadline;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

}
