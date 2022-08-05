package com.code.springmvcgenerator.repository;

import com.code.springmvcgenerator.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
