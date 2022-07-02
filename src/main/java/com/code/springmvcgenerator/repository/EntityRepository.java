package com.code.springmvcgenerator.repository;

import com.code.springmvcgenerator.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository extends JpaRepository<Entity, Long> {
}
