package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.Department;
import com.example.be_project.model.entities.User;
import com.example.be_project.model.entities.configuration.Configuration;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.repository.config.ConfigRepository;
import com.example.be_project.util.JWTUtil;
import com.example.be_project.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config-api")
public class ConfigController {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfigRepository configRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getAllConfigOfDepartment(@RequestHeader(name="Authorization") String token,
                                                    @RequestParam String department,
                                                    @RequestParam(required = false) Integer type,
                                                    @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Page<Configuration> pageConfigurations = configRepository.findAllByDepartmentKeyAndType
                    (department, type, PageRequest.of(pageNum, 10));
            return ResponseEntity.ok(new DataResponse<>(pageConfigurations.getContent(), pageConfigurations.getTotalPages()));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createConfigForDepartment(
                                              @RequestHeader(name="Authorization") String token,
                                              @RequestBody Configuration configuration) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))) {
            try {
                long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ", ""));
                User user = userRepository.findUserById(userId);
                configuration.setCreatedBy(user.getUsername());
                configuration.setStatus(1);
                configRepository.save(configuration);
                return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), "Tạo mới thành công!"));
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("Un-authentication");
    }
}
