package com.example.be_project.controller.configuration;

import com.example.be_project.model.entities.configuration.Work;
import com.example.be_project.model.request.ConfigRequest;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.config.WorkRepository;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api/job-config")
public class JobController {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private WorkRepository workRepository;


    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getJobs(@RequestHeader(name="Authorization") String token,
                                       @RequestParam(required = false) long storeId,
                                       @RequestParam int action,
                                       @RequestParam(required = false) int pageNum) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            //long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            return ResponseEntity.ok(workRepository.findAllByStoreIdAndAction(storeId, action, PageRequest.of(pageNum, 8)).getContent());
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("")
    public ResponseEntity<?> getJobsWithoutPage(@RequestHeader(name="Authorization") String token,
                                     @RequestParam(required = false) long storeId,@RequestParam int action) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(workRepository.findAllByStoreIdAndAction(storeId,action));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.PUT)
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editJob(@RequestHeader(name="Authorization") String token,
                                     @RequestBody ConfigRequest request,
                                        @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Work work = workRepository.findById(id).orElse(null);
            work.setName(request.getName());
            work.setDays(request.getDays());
            work.setHour(request.getHour());
            workRepository.save(work);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "Cập nhật thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJob(@RequestHeader(name="Authorization") String token,
                                          @PathVariable long id) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            Work work = workRepository.findById(id).orElse(null);
            workRepository.delete(work);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.OK.value(), "Xóa đối tượng thành công"));
        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> creatJob(@RequestHeader(name="Authorization") String token,
                                         @RequestBody ConfigRequest request) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            Work work = new Work();
            work.setName(request.getName());
            work.setAction(request.getAction());
            work.setCreatedBy(userId);
            work.setStoreId(request.getStoreId());
            work.setStatus(true);
            work.setDays(request.getDays());
            work.setHour(request.getHour());
            workRepository.save(work);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), "Tạo mới thành công"));
        }
        throw new Exception("Un-authentication");
    }
}
