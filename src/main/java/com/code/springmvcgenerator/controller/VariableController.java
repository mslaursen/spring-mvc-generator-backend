package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.Relation;
import com.code.springmvcgenerator.entity.Variable;
import com.code.springmvcgenerator.service.RelationService;
import com.code.springmvcgenerator.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/variables")
@CrossOrigin
public class VariableController {

    private final VariableService variableService;

    @Autowired
    public VariableController(VariableService variableService) {
        this.variableService = variableService;
    }

    @PostMapping
    public ResponseEntity<Variable> create(@RequestBody Variable variable) {
        Variable savedVariable = variableService.save(variable);
        return ResponseEntity.ok()
                .body(savedVariable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Variable> fetchById(@PathVariable Long id) {
        Variable foundVariable = variableService.findById(id);
        return ResponseEntity.ok()
                .body(foundVariable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variable> updateVariable(
            @RequestBody Variable variable,
            @PathVariable Long id) {

        Variable variableToUpdate = variableService.findById(id);

        variableToUpdate.setVal1(variable.getVal1());
        variableToUpdate.setVal2(variable.getVal2());
        variableToUpdate.setVal3(variable.getVal3());

        Variable updatedVariable = variableService.update(variableToUpdate);

        return ResponseEntity.ok()
                .body(updatedVariable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        variableService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
