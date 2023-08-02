package com.example.be_project.repository.config;

import com.example.be_project.model.entities.configuration.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByStoreId(long storeId);
}
