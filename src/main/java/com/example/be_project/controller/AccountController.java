package com.example.be_project.controller;

import com.example.be_project.model.entities.Office;
import com.example.be_project.model.entities.User;
import com.example.be_project.model.request.ConditionRequest;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.OfficeRepository;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping("/api-account")
public class AccountController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getAllAccounts(@RequestHeader(name="Authorization") String token,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) String department,
                                            @RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") Date createdAt,
                                            @RequestParam(required = false) Integer officeId,
                                            @RequestParam(required = false) Integer positionId,
                                            @RequestParam(required = false) int storeId,
                                            @RequestParam(required = false) int pageNum) throws Exception {
        token= token.replace("Bearer ","");
        if(jwtUtil.validateToken(token)){
            Page<User> accountPage = userRepository.findByStatusAndOfficeAndCreateAtAndPositionAndDepartmentKey
                    (status,officeId,createdAt,positionId ,storeId, name, department, PageRequest.of(pageNum, 10));
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "", accountPage.getContent(), accountPage.getTotalPages()));
        }
        throw new Exception("Un-authentication");
    }

}
