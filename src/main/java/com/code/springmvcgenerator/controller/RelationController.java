package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.Relation;
import com.code.springmvcgenerator.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relations")
@CrossOrigin
public class RelationController {

    private final RelationService relationService;

    @Autowired
    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @PostMapping
    public ResponseEntity<Relation> create(@RequestBody Relation relation) {
        Relation savedRelation = relationService.save(relation);
        return ResponseEntity.ok()
                .body(savedRelation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relation> fetchById(@PathVariable Long id) {
        Relation foundRelation = relationService.findById(id);
        return ResponseEntity.ok()
                .body(foundRelation);
    }
}
