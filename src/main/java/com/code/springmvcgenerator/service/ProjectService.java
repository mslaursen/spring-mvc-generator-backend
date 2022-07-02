package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.Project;
import com.code.springmvcgenerator.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(Project project) {
        return projectRepository.save(project);

    }

    public List<Project> read() {
       return projectRepository.findAll();
    }

    public Project update(Project project) {
        return projectRepository.save(project);

    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}
