package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.Project;
import com.code.springmvcgenerator.service.ProjectService;
import com.code.springmvcgenerator.service.ZipService;
import com.code.springmvcgenerator.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;
    private final ZipService zipService;

    @Autowired
    public ProjectController(ProjectService projectService, ZipService zipService) {
        this.projectService = projectService;
        this.zipService = zipService;
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> download(@RequestBody Project project) throws FileNotFoundException {
        String filesFolder = "files-test";
        String zipName = "test.zip";

        Util.clearFolder(filesFolder);

        zipService.zipEntities(project.getEntities(), filesFolder, zipName);

        File zipFile = new File(zipName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));

        Util.clearFolder(filesFolder);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="  + zipFile.getName())
                .contentLength(zipFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project) {
        Project savedProject = projectService.create(project);
        return ResponseEntity.ok()
                .body(savedProject);
    }

    @GetMapping
    public ResponseEntity<List<Project>> fetchAll() {
        List<Project> projects = projectService.findAll();
        return ResponseEntity.ok()
                .body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> fetchById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        return ResponseEntity.ok()
                .body(project);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Project>> fetchProjectsByUserId(@PathVariable Long id) {
        List<Project> found = projectService.findByUserId(id);
        return ResponseEntity.ok()
                .body(found);
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
