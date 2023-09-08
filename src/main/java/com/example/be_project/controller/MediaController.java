package com.example.be_project.controller;

import com.example.be_project.model.entities.Media;
import com.example.be_project.model.entities.User;
import com.example.be_project.model.entities.configuration.Form;
import com.example.be_project.model.request.ConfigRequest;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.MediaRepository;
import com.example.be_project.repository.UserRepository;
import com.example.be_project.service.MediaService;
import com.example.be_project.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/api-media")
public class MediaController {
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> getAllMyWorkMedia(@RequestHeader(name="Authorization") String token,
                                                @RequestParam(required = false) int pageNum,

                                                @RequestParam(required = false) long storeId) throws Exception {

        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            User user = userRepository.findUserById(userId);
            Page<Media> pageMedia;
            if(user.isAdmin()){
                pageMedia = mediaRepository.findAllByStoreId(storeId, PageRequest.of(pageNum,10));
            } else{
                pageMedia = mediaRepository.findAllByStoreIdAndCreatedBy(storeId, user.getUsername(), PageRequest.of(pageNum,10));
            } return ResponseEntity.ok(new DataResponse<>(pageMedia.getContent(), pageMedia.getTotalPages()));

        }
        throw new Exception("Un-authentication");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.PUT)
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editMedia(@RequestHeader(name="Authorization") String token,
                                       @PathVariable long id, @RequestBody Media body) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));

            return ResponseEntity.ok(new DataResponse(HttpStatus.OK.value(),
                    mediaService.editMedia(body, id)));
        }
        throw new Exception("Un-authentication");

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.DELETE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMedia(@RequestHeader(name="Authorization") String token,
                                         @PathVariable long id) throws Exception {

        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            return ResponseEntity.ok(new DataResponse(HttpStatus.OK.value(),
                    mediaService.deleteMedia(id)));
        }
        throw new Exception("Un-authentication");

    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = RequestMethod.POST)
    @PostMapping("/create")
    public ResponseEntity<?> createMedia(@RequestHeader(name="Authorization") String token,
                                        @RequestBody Media body) throws Exception {
        if(jwtUtil.validateToken(token.replace("Bearer ",""))){
            long userId = jwtUtil.getUserByIdfromJWT(token.replace("Bearer ",""));
            mediaRepository.save(body);
            return ResponseEntity.ok(new DataResponse<>(HttpStatus.CREATED.value(), "Tạo mới thành công"));
        }
        throw new Exception("Un-authentication");
    }
}
