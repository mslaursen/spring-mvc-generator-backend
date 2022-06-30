package com.code.springmvcgenerator.repository;

import com.code.springmvcgenerator.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RelationRepository extends JpaRepository<Relation, Long> {
}
