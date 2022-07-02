package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.Project;
import com.code.springmvcgenerator.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project) {
        Project savedProject = projectService.create(project);
        return ResponseEntity.ok()
                .body(savedProject);
    }

    @GetMapping
    public ResponseEntity<List<Project>> read() {
        List<Project> projects = projectService.read();
        return ResponseEntity.ok()
                .body(projects);
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody Project project) {
        Project updatedProject = projectService.update(project);
        return ResponseEntity.ok()
                .body(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteById(@PathVariable Long id) {
        projectService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
