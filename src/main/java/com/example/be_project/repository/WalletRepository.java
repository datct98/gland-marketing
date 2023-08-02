package com.example.be_project.repository;

import com.example.be_project.model.dto.UserWalletDTO;
import com.example.be_project.model.entities.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("select new com.example.be_project.model.dto.UserWalletDTO(u.fullName, u.positionId, w.name, w.amount) " +
            "from User u " +
            "JOIN Wallet w ON w.userId= u.id " +
            "where u.storeId =:storeId")
    Page<UserWalletDTO> findAllWallets(@Param("storeId") long storeId, Pageable pageable);
}
