package com.example.be_project.controller;

import com.example.be_project.model.entities.Department;
import com.example.be_project.model.entities.Task;
import com.example.be_project.model.entities.User;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.TaskRepository;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.service.TaskService;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-api")
public class TaskController {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getAllTaskOfDepartment(@RequestHeader(name="Authorization") String token,
                                              @RequestParam(required = false) String department,
                                              @RequestParam(required = false) String assignee,
                                              @RequestParam(required = false) String creator,
                                              @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            //department null -> Lấy danh sách task hiển thị trên tổng
            //assignee null -> lấy danh sách nv của tôi
            // creator null -> Lấy ds nv được giao
            Page<Task> taskPage = taskRepository.findAllByDepartmentKeyAndUser
                    (department, assignee, creator, PageRequest.of(pageNum, 10));
            return ResponseEntity.ok(new DataResponse<>(taskPage.getContent(), taskPage.getTotalPages()));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createTaskOfDepartment(@RequestHeader(name="Authorization") String token,
                                              @RequestBody Task body) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))) {
            try {
                String username = jwtUtil.getUsernameFromJwt(token.replace("Bearer ",""));
                taskService.createTask(body, username);
                Page<Task> taskPage = taskRepository.findAllByDepartmentKey(body.getDepartmentKey(), PageRequest.of(0,10));
                return ResponseEntity.ok(new DataResponse<>(taskPage.getContent(), taskPage.getTotalPages()));
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("Un-authentication");
    }
}
