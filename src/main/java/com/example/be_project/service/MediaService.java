package com.example.be_project.service;

import com.example.be_project.model.entities.Media;
import com.example.be_project.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    public String editMedia(Media body, long id){
        Media media = mediaRepository.findById(id).orElseThrow();
        media.setFormId(body.getFormId());
        media.setLink(body.getLink());
        media.setNote(body.getNote());
        media.setPersonId(body.getPersonId());
        media.setServiceId(body.getServiceId());
        media.setSource(body.getSource());
        media.setCreatedBy(body.getCreatedBy());
        media.setWorkId(body.getWorkId());
        mediaRepository.save(media);
        return "Cập nhật thành công";
    }

    public String deleteMedia(long id){
        Media media = mediaRepository.findById(id).orElseThrow();
        mediaRepository.delete(media);
        return "Xóa thành công!";
    }
}
