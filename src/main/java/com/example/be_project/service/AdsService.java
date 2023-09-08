package com.example.be_project.service;

import com.example.be_project.model.entities.Ads;
import com.example.be_project.model.entities.Media;
import com.example.be_project.repository.AdsRepository;
import com.example.be_project.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdsService {
    @Autowired
    private AdsRepository adsRepository;

    public String editAds(Ads body, long id){
        Ads ads = adsRepository.findById(id).orElseThrow();
        ads.setFormId(body.getFormId());
        ads.setLink(body.getLink());
        ads.setNote(body.getNote());
        ads.setPersonId(body.getPersonId());
        ads.setServiceId(body.getServiceId());
        ads.setStatusId(body.getStatusId());
        ads.setSource(body.getSource());
        ads.setAssignee(body.getAssignee());
        ads.setDeadline(body.getDeadline());
        ads.setWorkId(body.getWorkId());
        adsRepository.save(ads);
        return "Cập nhật thành công";
    }

    public String deleteAds(long id){
        Ads ads = adsRepository.findById(id).orElseThrow();
        adsRepository.delete(ads);
        return "Xóa thành công!";
    }
}
