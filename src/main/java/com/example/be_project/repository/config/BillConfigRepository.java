package com.example.be_project.repository.config;

import com.example.be_project.model.dto.UserBillDTO;
import com.example.be_project.model.entities.configuration.BillConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface BillConfigRepository extends JpaRepository<BillConfig, Long> {
    @Query("SELECT new com.example.be_project.model.dto.UserBillDTO(b.name, b.type, b.status, u.fullName, b.createdAt, b.id) FROM BillConfig b JOIN User u " +
            "ON b.userId = u.id where (:status is null or b.status =:status) and (:type is null or b.type =:type)")
    Page<UserBillDTO> findAllBillConfigByCondition(@Param("status") Integer status, @Param("type") Integer type, Pageable pageable);

    @Query("select bc from BillConfig bc where bc.status =:status and bc.type=:type")
    List<BillConfig> findAllByTypeAndStatus(@Param("status") boolean status, @Param("type") boolean type);
}
