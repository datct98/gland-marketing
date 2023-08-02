package com.example.be_project.repository.config;

import com.example.be_project.model.entities.configuration.Person;
import com.example.be_project.model.entities.configuration.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByStoreId(long storeId);
}
