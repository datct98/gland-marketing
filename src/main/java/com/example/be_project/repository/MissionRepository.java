package com.example.be_project.repository;

import com.example.be_project.model.dto.MissionDepartDTO;
import com.example.be_project.model.entities.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    Page<Mission> findAllByStoreId(long storeId, Pageable pageable);

    @Query("select new com.example.be_project.model.dto.MissionDepartDTO(m.id, m.name, d.name, m.createdBy, m.createdAt) " +
            "from Mission m left Join Department d on m.departmentKey like d.keyUUID " +
            "where m.storeId =:storeId " +
            "and (:department is null or m.departmentKey like :department)")
    Page<MissionDepartDTO> findAllByStoreIdAndDepartmentKey(long storeId, String department, Pageable pageable);
    List<Mission> findAllByStoreId(long storeId);

}
