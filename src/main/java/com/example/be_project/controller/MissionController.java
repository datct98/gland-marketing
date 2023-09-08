package com.example.be_project.controller;

import com.example.be_project.model.dto.MissionDepartDTO;
import com.example.be_project.model.entities.Department;
import com.example.be_project.model.entities.Media;
import com.example.be_project.model.entities.Mission;
import com.example.be_project.model.entities.User;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.MissionRepository;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-mission")
@Slf4j
public class MissionController {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MissionRepository missionRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getAllMissionPaging(@RequestHeader(name="Authorization") String token,
                                                 @RequestParam(required = false) int pageNum,
                                                 @RequestParam(required = false) long storeId,
                                                 @RequestParam(required = false) String department) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Page<MissionDepartDTO> missionPage = missionRepository.findAllByStoreIdAndDepartmentKey(storeId, department, PageRequest.of(pageNum, 10));
            return ResponseEntity.ok(new DataResponse<>(missionPage.getContent(), missionPage.getTotalPages()));

        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("")
    public ResponseEntity<?> getAllMission(@RequestHeader(name="Authorization") String token,
                                           @RequestParam(required = false) long storeId,
                                           @RequestParam(required = false) String department) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            List<Mission> missions = missionRepository.findAllByStoreId(storeId);
            return ResponseEntity.ok(new DataResponse<>(missions));

        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createMission(@RequestHeader(name="Authorization") String token,
                                           @RequestBody Mission mission) throws Exception {

        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            try {
                long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
                User user = userRepository.findUserById(userId);
                mission.setCreatedBy(user.getUsername());
                missionRepository.save(mission);
                return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(),"Tạo mới thành công!")) ;
            } catch (Exception e){
                throw e;
            }
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/my-mission-assignee")
    public ResponseEntity<?> getAllMyMissionPaging(@RequestHeader(name="Authorization") String token,
                                                 @RequestParam(required = false) int pageNum,
                                                 @RequestParam(required = false) long storeId,
                                                 @RequestParam String department,
                                                 @RequestParam(required = false) String creator,
                                                 @RequestParam(required = false) String assignee) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Page<MissionDepartDTO> missionPage = missionRepository.findAllByStoreIdAndDepartmentKey(storeId, department, PageRequest.of(pageNum, 10));
            return ResponseEntity.ok(new DataResponse<>(missionPage.getContent(), missionPage.getTotalPages()));

        }
        throw new Exception("Un-authentication");
    }
}
