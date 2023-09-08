package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.configuration.Service;
import com.example.be_project.model.entities.configuration.Status;
import com.example.be_project.model.request.ConfigRequest;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.config.ServiceRepository;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api/service-config")
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getStatus(@RequestHeader(name="Authorization") String token,
                                       @RequestParam int action,
                                       @RequestParam(required = false) long storeId) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(serviceRepository.findAllByStoreIdAndAction(storeId, action));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("")
    public ResponseEntity<?> getStatusWithoutPage(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId,
                                       @RequestParam int action,
                                       @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(serviceRepository.findAllByStoreIdAndAction(storeId, action, PageRequest.of(pageNum, 8)).getContent());
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createStatus(@RequestHeader(name="Authorization") String token,
                                          @RequestBody ConfigRequest request) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            Service service = new Service();
            service.setName(request.getName());
            service.setAction(request.getAction());
            service.setCreatedBy(userId);
            service.setStoreId(request.getStoreId());
            service.setStatus(true);
            serviceRepository.save(service);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), "Tạo mới thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @PutMapping("/edit/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.PUT)
    public ResponseEntity<?> editStatus(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id, @RequestParam String name) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));

            Service service = serviceRepository.findById(id).orElse(null);
            service.setName(name);
            service.setCreatedBy(userId);
            serviceRepository.save(service);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "Cập nhật thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStatus(@RequestHeader(name="Authorization") String token,
                                          @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Service service = serviceRepository.findById(id).orElse(null);
            serviceRepository.delete(service);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(),"Xóa trạng thái thành công!") );
        }
        throw new Exception("Un-authentication");
    }
}
