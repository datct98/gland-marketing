package com.example.be_project.repository.config;

import com.example.be_project.model.dto.ConfigDTO;
import com.example.be_project.model.entities.configuration.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query("SELECT new com.example.be_project.model.dto.ConfigDTO(s.id, s.name, s.createdAt, u.fullName, s.status) " +
            "from Status s " +
            "JOIN User u on s.createdBy = u.id " +
            "where s.storeId = :storeId and s.action =:action")
    Page<ConfigDTO> findAllByStoreIdAndAction(@Param("storeId") long storeId, int action, Pageable pageable);

    List<Status>  findAllByStoreIdAndAction(long storeId, int action);
}
