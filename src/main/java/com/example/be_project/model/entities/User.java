package com.example.be_project.model.entities;

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
    @Column(name= "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name= "username")
    String username;

    @Column(name= "full_name")
    String fullName;

    @Column(name= "password")
    String password;

    @Column(name= "position_id")
    int positionId;

    @Column(name= "phone")
    String phone;

    @Column(name= "address")
    String address;

    @Column(name= "email")
    String email;

    @Column(name= "status")
    String status;

    @Column(name = "office_id")
    int officeId;

    @Column(name = "store_id")
    long storeId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

}
