package com.code.springmvcgenerator.repository;

import com.code.springmvcgenerator.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
