package com.example.be_project.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="User")
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String username;

    @Column(name= "full_name")
    String fullName;

    //@JsonIgnore
    String password;

    @Column(name= "position_id")
    int positionId;

    String position;

    String phone;

    String address;

    String email;

    @Column(name = "department_key")
    String departmentKey;

    @Column(name= "status")
    String status;

    @Column(name = "office_id")
    Integer officeId;

    @Column(name = "is_admin")
    boolean admin;

    @Column(name = "created_by")
    String createdBy;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

}
