package com.example.be_project.repository;

import com.example.be_project.model.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Page<Department> findAllByStoreId(long storeId, Pageable pageable);
    List<Department> findAllByStoreId(long storeId);
}
