package com.example.be_project.repository;

import com.example.be_project.model.entities.Ads;
import com.example.be_project.model.entities.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdsRepository extends JpaRepository<Ads, Long> {
    Page<Ads> findAllByStoreIdAndCreatedBy(long storeId, String createdBy, Pageable pageable);
    Page<Ads> findAllByStoreId(long storeId, Pageable pageable);
}
