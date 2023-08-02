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

    @Query("select u from User u where (:status is null or u.status = :status)" +
            "and (:createAt is null or u.createdAt = :createAt)" +
            "and (:position is null or u.positionId = :positionId)" +
            "and ( u.storeId = :storeId)" +
            "and (:name is null or u.fullName like %:name% or u.username like %:name%)" +
            "and (:office is null or u.officeId = :officeId)")
    Page<User> findByStatusAndOfficeAndCreateAtAndPosition(
                                                @Param("status") String status,
                                                @Param("office") int officeId,
                                                @Param("createAt") Date createAt,
                                                @Param("position") int positionId,
                                                @Param("storeId") long storeId,
                                                @Param("name") String name,
                                                Pageable pageable);

}
