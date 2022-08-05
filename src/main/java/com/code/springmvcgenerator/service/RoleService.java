package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.Role;
import com.code.springmvcgenerator.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }

    public Role update(Role toUpdate) {
        return roleRepository.save(toUpdate);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

}
