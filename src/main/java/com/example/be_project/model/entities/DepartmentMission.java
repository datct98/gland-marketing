package com.example.be_project.model.entities;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;

@Entity
@Table(name = "department_mission")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "department_key")
    String departmentKey;
    @Column(name = "mission_key")
    String missionKey;
    @Column(name = "is_selected")
    boolean selected;
}
