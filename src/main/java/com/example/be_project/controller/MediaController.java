package com.example.be_project.controller;

import com.example.be_project.model.entities.Media;
import com.example.be_project.model.response.DataResponse;
import com.example.be_project.repository.MediaRepository;
import com.example.be_project.service.MediaService;
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllMedia(@RequestParam(required = false) int pageNum,
                                         @RequestParam(required = false) long storeId){
        Page<Media> pageMedia = mediaRepository.findAllByStoreId(storeId, PageRequest.of(pageNum,10));
        return ResponseEntity.ok(pageMedia);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editMedia(@PathVariable long id, @RequestBody Media body){
        return ResponseEntity.ok(new DataResponse(HttpStatus.OK.value(),
                mediaService.editMedia(body, id)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMedia(@PathVariable long id){
        return ResponseEntity.ok(new DataResponse(HttpStatus.OK.value(),
                mediaService.deleteMedia(id)));
    }
}
