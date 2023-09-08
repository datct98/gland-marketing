package com.example.be_project.repository;

import com.example.be_project.model.entities.DepartmentMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentMissionRepository extends JpaRepository<DepartmentMission, Long> {
    DepartmentMission findByDepartmentKeyAndMissionKey(String dKey, String mKey);
}
