package com.code.springmvcgenerator.repository;

import com.code.springmvcgenerator.entity.Variable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariableRepository extends JpaRepository<Variable, Long> {
}
