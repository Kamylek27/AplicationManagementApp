package com.kamil.aplicationmanagementapp.repository;

import com.kamil.aplicationmanagementapp.entity.HistoryOfApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.Optional;

@Repository
public interface HistoryOfApplicationRepository extends JpaRepository<HistoryOfApplication, Id> {

    Optional<HistoryOfApplication> findById(Long id);
}
