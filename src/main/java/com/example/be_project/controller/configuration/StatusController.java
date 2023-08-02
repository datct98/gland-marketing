package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.configuration.Status;
import com.example.be_project.repository.config.StatusRepository;
import com.example.be_project.service.configuration.StatusService;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api/status-config")
public class StatusController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private StatusService statusService;

    @GetMapping("/all")
    public ResponseEntity<?> getStatus(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            //long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            return ResponseEntity.ok(statusService.getStatusByStoreId(storeId));
        }
        throw new Exception("Un-authentication");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editStatus(@RequestHeader(name="Authorization") String token,
                                        @PathVariable int id, @RequestParam String name) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(statusService.editStatus(id, name));
        }
        throw new Exception("Un-authentication");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStatus(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Status status = statusRepository.findById(id).orElse(null);
            statusRepository.delete(status);
            return ResponseEntity.ok("Xóa trạng thái thành công!");
        }
        throw new Exception("Un-authentication");
    }
}
