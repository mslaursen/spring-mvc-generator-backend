package com.code.springmvcgenerator.repository;

import com.code.springmvcgenerator.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntityRepository extends JpaRepository<Entity, Long> {

    @Query("SELECT e FROM Entity e WHERE e.project.id = ?1")
    List<Entity> findByProjectId(Long id);
}
