package com.example.be_project.model.entities;

import com.example.be_project.model.entities.configuration.Form;
import com.example.be_project.model.entities.configuration.Person;
import com.example.be_project.model.entities.configuration.Service;
import com.example.be_project.model.entities.configuration.Work;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Media {
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

    @Column(name = "created_by")
    private long createdBy; //userId
    private String link;
    private String note;

    @Column(name = "store_id")
    private long storeId;

}
