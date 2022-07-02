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

    @PutMapping("/{id}")
    public ResponseEntity<Relation> updateRelation(
            @RequestBody Relation relation,
            @PathVariable Long id) {

        Relation relationToUpdate = relationService.findById(id);

        relationToUpdate.setVal1(relation.getVal1());
        relationToUpdate.setVal2(relation.getVal2());
        relationToUpdate.setVal3(relation.getVal3());

        Relation updatedRelation = relationService.update(relationToUpdate);

        return ResponseEntity.ok()
                .body(updatedRelation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        relationService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
