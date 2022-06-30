package com.code.springmvcgenerator.repository;

import com.code.springmvcgenerator.entity.EntityDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityDetailRepository extends JpaRepository<EntityDetail, Long> {
}
