package com.example.be_project.repository.config;

import com.example.be_project.model.dto.ConfigDTO;
import com.example.be_project.model.entities.configuration.Person;
import com.example.be_project.model.entities.configuration.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findAllByStoreIdAndAction(long storeId, int action);

    @Query("SELECT new com.example.be_project.model.dto.ConfigDTO(s.id, s.name, s.createdAt, u.fullName, s.status, s.days, s.hour) " +
            "from Work s " +
            "JOIN User u on s.createdBy = u.id " +
            "where s.storeId = :storeId and s.action=:action")
    Page<ConfigDTO> findAllByStoreIdAndAction(@Param("storeId") long storeId, int action, Pageable pageable);
}
