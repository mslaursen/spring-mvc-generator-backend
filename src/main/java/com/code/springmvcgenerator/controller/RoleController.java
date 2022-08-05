package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.Role;
import com.code.springmvcgenerator.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Role role) {
        Role savedRole = roleService.save(role);
        return ResponseEntity.ok()
            .body(savedRole);
    }

    @GetMapping
    public ResponseEntity<List<Role>> fetchAll() {
        List<Role> found = roleService.findAll();
        return ResponseEntity.ok()
            .body(found);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> fetchById(@PathVariable Long id) {
        Role found = roleService.findById(id);
        return ResponseEntity.ok()
            .body(found);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateById(@RequestBody Role role, @PathVariable Long id) {
        Role toUpdate = roleService.findById(id);
        toUpdate.setName(role.getName());

        Role updated = roleService.update(toUpdate);
        return ResponseEntity.ok()
            .body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.ok()
            .build();
    }

}
