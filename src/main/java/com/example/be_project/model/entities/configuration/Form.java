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
public class Form { // Hình thức
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name = "created_by")
    private long createdBy;
    private boolean status;
    private int action; //Nhiệm vụ media/ ads

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "store_id")
    private long storeId;
}
