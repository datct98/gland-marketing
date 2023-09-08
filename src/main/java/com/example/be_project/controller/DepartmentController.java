package com.example.be_project.controller;

import com.example.be_project.model.entities.Department;
import com.example.be_project.model.entities.User;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.DepartmentRepository;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api-department")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(@RequestHeader(name="Authorization") String token,
                                              @RequestBody Department department) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))) {
            try {
                long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ", ""));
                User user = userRepository.findUserById(userId);
                department.setCreatedBy(user.getUsername());
                departmentRepository.save(department);
                return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), "Tạo mới phòng ban " + department.getName() + " thành công!"));
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("")
    public ResponseEntity<?> getAllDepartmentWithoutPaging(@RequestHeader(name="Authorization") String token,
                                           @RequestParam(required = false) long storeId) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            List<Department> departments = departmentRepository.findAllByStoreId(storeId);
            return ResponseEntity.ok(new DataResponse<>(departments));

        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartment(@RequestHeader(name="Authorization") String token,
                                              @RequestParam(required = false) long storeId,
                                              @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Page<Department> departments = departmentRepository.findAllByStoreId(storeId, PageRequest.of(pageNum, 10));
            return ResponseEntity.ok(new DataResponse<>(departments.getContent(), departments.getTotalPages()));

        }
        throw new Exception("Un-authentication");
    }
}
