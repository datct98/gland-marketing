package com.example.be_project.repository.config;

import com.example.be_project.model.entities.configuration.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findAllByStoreId(long storeId);

}
