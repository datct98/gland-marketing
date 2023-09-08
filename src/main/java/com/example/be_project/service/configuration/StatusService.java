package com.example.be_project.service.configuration;

import com.example.be_project.model.dto.ConfigDTO;
import com.example.be_project.model.entities.configuration.Status;
import com.example.be_project.repository.config.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    //@Cacheable(key = "userId", value = "status")
    public Page<ConfigDTO> getStatusByStoreId(long storeId, int action, int pageNum){
        return statusRepository.findAllByStoreIdAndAction(storeId, action, PageRequest.of(pageNum, 8));
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

    public String createStatus(long storeId, String name, long userId, int action){
        Status status = new Status();
        status.setName(name);
        status.setAction(action);
        status.setCreatedBy(userId);
        status.setStoreId(storeId);
        status.setStatus(true);
        statusRepository.save(status);
        return "Tạo thành công";
    }
}
