package com.example.be_project.repository;

import com.example.be_project.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);

    User findUserById(Long id);

    @Query("select u from User u JOIN UserStore us " +
            "On u.id = us.userId where (:status is null or u.status = :status)" +
            "and (:createdAt is null or u.createdAt = :createdAt) " +
            "and us.storeId =:storeId "+
            "and (:positionId is null or u.positionId = :positionId) " +
            "and (:name is null or u.fullName like %:name% or u.username like %:name%) " +
            "and (:department is null or u.departmentKey like :department) " +
            "and (:officeId is null or u.officeId = :officeId)")
    Page<User> findByStatusAndOfficeAndCreateAtAndPositionAndDepartmentKey(
                                                @Param("status") String status,
                                                @Param("officeId") Integer officeId,
                                                @Param("createdAt") Date createdAt,
                                                @Param("positionId") Integer positionId,
                                                @Param("storeId") long storeId,
                                                @Param("name") String name,
                                                @Param("department") String department,
                                                Pageable pageable);

}
