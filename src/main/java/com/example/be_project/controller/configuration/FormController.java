package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.configuration.Form;
import com.example.be_project.model.request.ConfigRequest;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.config.FormRepository;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api/form-config")
public class FormController {
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getForm(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId,
                                       @RequestParam int action,
                                       @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(formRepository.findAllByStoreIdAndAction(storeId, action, PageRequest.of(pageNum, 8)).getContent());
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("")
    public ResponseEntity<?> getFormWithoutPage(@RequestHeader(name="Authorization") String token,
                                     @RequestParam(required = false) long storeId, @RequestParam int action) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(formRepository.findAllByStoreIdAndAction(storeId, action));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createForm(@RequestHeader(name="Authorization") String token,
                                          @RequestBody ConfigRequest request) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            Form form = new Form();
            form.setName(request.getName());
            form.setAction(request.getAction());
            form.setCreatedBy(userId);
            form.setStoreId(request.getStoreId());
            form.setStatus(true);
            formRepository.save(form);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), "Tạo mới thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.PUT)
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editForm(@RequestHeader(name="Authorization") String token,
                                        @PathVariable long id, @RequestParam String name) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Form form = formRepository.findById(id).orElse(null);
            form.setName(name);
            formRepository.save(form);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "Cập nhật thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteForm(@RequestHeader(name="Authorization") String token,
                                          @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Form form = formRepository.findById(id).orElse(null);
            formRepository.delete(form);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "Xóa đối tượng thành công"));
        }
        throw new Exception("Un-authentication");
    }
}
