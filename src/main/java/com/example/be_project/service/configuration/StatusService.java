package com.example.be_project.service.configuration;

import com.example.be_project.model.entities.configuration.Status;
import com.example.be_project.repository.config.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    //@Cacheable(key = "userId", value = "status")
    public List<Status> getStatusByStoreId(long storeId){
        return statusRepository.findAllByStoreId(storeId);
    }

    public String editStatus(long id, String name){
        Status status = statusRepository.findById(id).orElse(null);
        if(status!=null){
            status.setName(name);
            statusRepository.save(status);
            return "Cập nhật thành công";
        }
        return "Không tìm thấy status cần chỉnh sửa";
    }
}
