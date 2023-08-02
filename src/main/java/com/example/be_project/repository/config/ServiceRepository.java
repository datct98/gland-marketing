package com.example.be_project.repository.config;

import com.example.be_project.model.entities.configuration.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findAllByStoreId(long storeId);
}
