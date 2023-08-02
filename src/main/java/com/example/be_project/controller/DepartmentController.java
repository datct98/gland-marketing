package com.example.be_project.controller;

import com.example.be_project.model.entities.Department;
import com.example.be_project.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marketing/api-department")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/create")
    public String createDepartment(@RequestBody Department department){
        try {
            departmentRepository.save(department);
            return "Tạo mới phòng ban "+department.getName()+" thành công!";
        } catch (Exception e){
            throw e;
        }
    }
}
