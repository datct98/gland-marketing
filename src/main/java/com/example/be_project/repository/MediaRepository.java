package com.example.be_project.repository;

import com.example.be_project.model.entities.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
    Page<Media> findAllByStoreIdAndCreatedBy(long storeId, String createdBy, Pageable pageable);
    Page<Media> findAllByStoreId(long storeId, Pageable pageable);
}
