package com.example.be_project.repository.config;

import com.example.be_project.model.entities.configuration.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConfigRepository extends JpaRepository<Configuration, Long> {
    @Query("select c from Configuration c where c.departmentKey like :department " +
            "and (:type is null or c.type =:type)")
    Page<Configuration> findAllByDepartmentKeyAndType(String department, Integer type, Pageable pageable);
}
