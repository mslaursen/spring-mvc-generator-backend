package com.code.springmvcgenerator.repository;

import com.code.springmvcgenerator.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.user.id = ?1")
    List<Project> findByUserId(Long id);
}
