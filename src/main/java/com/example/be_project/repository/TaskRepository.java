package com.example.be_project.repository;

import com.example.be_project.model.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task>findAllByDepartmentKey(String department, Pageable pageable);

    @Query("select t from Task t where (:department is null or t.departmentKey like :department)  " +
            "and (:assignee is null or t.assignee like :assignee) " +
            "and (:creator is null or t.createdBy like :creator)")
    Page<Task>findAllByDepartmentKeyAndUser(String department, String assignee, String creator, Pageable pageable);
}
