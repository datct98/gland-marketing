package com.example.be_project.repository;

import com.example.be_project.model.entities.UserStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoreRepository extends JpaRepository<UserStore, Long> {
}
