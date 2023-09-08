package com.example.be_project.controller;

import com.example.be_project.model.entities.DepartmentMission;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.DepartmentMissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api-department-mission")
public class DepartmentMissionController {
    @Autowired
    private DepartmentMissionRepository departmentMissionRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/add") // gán nhiệm vụ cho các phòng ban
    public ResponseEntity<?> addMissionDepartment(@RequestBody List<DepartmentMission> body){
        try {
            List<DepartmentMission> departmentMissions = new ArrayList<>();
            for (DepartmentMission dm: body){
                DepartmentMission departmentMission = departmentMissionRepository.findByDepartmentKeyAndMissionKey(dm.getDepartmentKey(), dm.getMissionKey());
                if(departmentMission==null){
                    departmentMissions.add(dm);
                } else {
                    departmentMission.setSelected(dm.isSelected());
                    departmentMissions.add(departmentMission);
                }
            }
            departmentMissionRepository.saveAll(departmentMissions);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(),"")) ;
        } catch (Exception e){
            throw e;
        }
    }
}
