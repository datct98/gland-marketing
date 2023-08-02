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
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/marketing/api-account")
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
                                            @RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") Date createAt,
                                            @RequestParam(required = false) int officeId,
                                            @RequestParam(required = false) int positionId,
                                            @RequestParam(required = false) long storeId,
                                            @RequestParam(required = false) int pageNum) throws Exception {
        token= token.replace("Bearer ","");
        if(jwtUtil.validateToken(token)){
            Page<User> accountPage = userRepository.findByStatusAndOfficeAndCreateAtAndPosition(status,officeId,createAt,positionId ,storeId, name, PageRequest.of(pageNum, 10));
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "", accountPage.getContent()));
        }
        throw new Exception("Un-authentication");
    }

}
