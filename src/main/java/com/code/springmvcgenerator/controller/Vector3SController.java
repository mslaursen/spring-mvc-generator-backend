package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.Vector3S;
import com.code.springmvcgenerator.service.Vector3SService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vector3s")
@CrossOrigin
public class Vector3SController {

    private final Vector3SService vector3SService;

    @Autowired
    public Vector3SController(Vector3SService vector3SService) {
        this.vector3SService = vector3SService;
    }

    @PostMapping
    public ResponseEntity<Vector3S> create(@RequestBody Vector3S vector) {
        Vector3S savedVector3S = vector3SService.save(vector);
        return ResponseEntity.ok()
                .body(savedVector3S);
    }
}
