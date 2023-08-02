package com.example.be_project.model.entities.configuration;

import com.example.be_project.model.entities.Media;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Accessors(chain = true)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;
    @Column(name = "created_by")
    private long userId;
    private boolean status;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    private int action; //Nhiệm vụ media/ ads
    @Column(name = "store_id")
    private long storeId;
}
