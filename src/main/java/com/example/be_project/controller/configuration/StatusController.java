package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.configuration.Status;
import com.example.be_project.model.request.ConfigRequest;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.config.StatusRepository;
import com.example.be_project.service.configuration.StatusService;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getStatus(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId,
                                       @RequestParam int action,
                                       @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(statusService.getStatusByStoreId(storeId, action, pageNum).getContent());
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("")
    public ResponseEntity<?> getStatusWithoutPage(@RequestHeader(name="Authorization") String token,
                                       @RequestParam int action,
                                       @RequestParam(required = false) long storeId) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(statusRepository.findAllByStoreIdAndAction(storeId, action));
        }
        throw new Exception("Un-authentication");
    }

    @PutMapping("/edit/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.PUT)
    public ResponseEntity<?> editStatus(@RequestHeader(name="Authorization") String token,
                                        @PathVariable int id, @RequestParam String name) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), statusService.editStatus(id, name)));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStatus(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Status status = statusRepository.findById(id).orElse(null);
            if(status!= null)
                statusRepository.delete(status);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(),"Xóa trạng thái thành công!") );
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createStatus(@RequestHeader(name="Authorization") String token,
                                          @RequestBody ConfigRequest request) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), statusService.createStatus(request.getStoreId(), request.getName(), userId, request.getAction())));
        }
        throw new Exception("Un-authentication");
    }
}
