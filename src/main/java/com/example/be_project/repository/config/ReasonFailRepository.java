package com.example.be_project.repository.config;

import com.example.be_project.model.dto.ConfigDTO;
import com.example.be_project.model.entities.configuration.FailReason;
import com.example.be_project.model.entities.configuration.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReasonFailRepository extends JpaRepository<FailReason, Long> {
    List<FailReason> findAllByStoreIdAndAction(long storeId, int action);

    @Query("SELECT new com.example.be_project.model.dto.ConfigDTO(s.id, s.reason, s.createdAt, u.fullName, s.status) " +
            "from FailReason s " +
            "JOIN User u on s.createdBy = u.id " +
            "where s.storeId = :storeId and s.action =:action")
    Page<ConfigDTO> findPageByStoreIdAndAction(@Param("storeId") long storeId, @Param("action") int action, Pageable pageable);
}
