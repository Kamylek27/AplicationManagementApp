package com.kamil.aplicationmanagementapp.repository;

import com.kamil.aplicationmanagementapp.entity.Application;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findById(Long id);

    Application findByName(String name);


}
