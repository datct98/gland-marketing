package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.configuration.FailReason;
import com.example.be_project.model.entities.configuration.Form;
import com.example.be_project.model.request.ConfigRequest;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.config.FormRepository;
import com.example.be_project.repository.config.ReasonFailRepository;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api/fail-config")
public class FailReasonController {
    @Autowired
    private ReasonFailRepository reasonFailRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getReason(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId,  @RequestParam int action,
                                       @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(reasonFailRepository.findPageByStoreIdAndAction(storeId, action, PageRequest.of(pageNum, 8)).getContent());
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("")
    public ResponseEntity<?> getFormWithoutPage(@RequestHeader(name="Authorization") String token,
                                     @RequestParam int action,
                                     @RequestParam(required = false) long storeId) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(reasonFailRepository.findAllByStoreIdAndAction(storeId, action));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createReason(@RequestHeader(name="Authorization") String token,
                                          @RequestBody ConfigRequest request) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            FailReason reason = new FailReason();
            reason.setReason(request.getName());
            reason.setAction(request.getAction());
            reason.setCreatedBy(userId);
            reason.setStoreId(request.getStoreId());
            reason.setStatus(true);
            reasonFailRepository.save(reason);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), "Tạo mới thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.PUT)
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editReason(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id, @RequestParam String name) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            FailReason reason = reasonFailRepository.findById(id).orElse(null);
            if(reason!= null){
                reason.setReason(name);
                reasonFailRepository.save(reason);
            }
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "Cập nhật thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteForm(@RequestHeader(name="Authorization") String token,
                                          @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            FailReason reason = reasonFailRepository.findById(id).orElse(null);
            if(reason!= null)
            reasonFailRepository.delete(reason);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "Xóa đối tượng thành công"));
        }
        throw new Exception("Un-authentication");
    }
}
