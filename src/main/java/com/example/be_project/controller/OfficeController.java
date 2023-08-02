package com.example.be_project.controller;

import com.example.be_project.model.entities.Office;
import com.example.be_project.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OfficeController {
    @Autowired
    private OfficeRepository officeRepository;

    @PostMapping("/create-office")
    public String createOffice(@RequestBody Office office){
        try {
             officeRepository.save(office);
             return "Tạo mới chức vụ "+office.getName()+" thành công!";
        } catch (Exception e){
            throw e;
        }
    }
}
