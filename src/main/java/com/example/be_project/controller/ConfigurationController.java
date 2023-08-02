package com.example.be_project.controller;

import com.example.be_project.model.entities.configuration.Status;
import com.example.be_project.repository.config.StatusRepository;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api-config")
public class ConfigurationController {
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/status")
    public ResponseEntity<?> getAllStatus(){
        return ResponseEntity.ok(statusRepository.findAll());
    }

    @PutMapping("/status/edit/{id}")
    public ResponseEntity<?> editStatus(@PathVariable long id, @RequestParam String name
            , @RequestHeader(name="Authorization") String token) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Status status = statusRepository.findById(id).orElseThrow();
            status.setName(name);
            statusRepository.save(status);
            return ResponseEntity.ok("Cập nhật thành công");
        }
        throw new Exception("Un-authentication");
    }

    @DeleteMapping("/status/delete/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable long id, @RequestHeader(name="Authorization") String token) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Status status = statusRepository.findById(id).orElseThrow();
            statusRepository.delete(status);
            return ResponseEntity.ok("Xóa thành công");
        }
        throw new Exception("Un-authentication");
    }

    @PostMapping("/status/create")
    public ResponseEntity<?> editStatus(@RequestParam String name, @RequestHeader(name="Authorization") String token) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Status status = new Status();
            status.setName(name);
            statusRepository.save(status);
            return ResponseEntity.ok("Cập nhật thành công");
        }
        throw new Exception("Un-authentication");
    }
}
