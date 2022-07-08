package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.Project;
import com.code.springmvcgenerator.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(Project project) {
        return projectRepository.save(project);

    }

    public List<Project> findAll() {
       return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow();
    }

    public Project update(Project project) {
        return projectRepository.save(project);

    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> findByUserId(Long id) {
        return projectRepository.findByUserId(id);
    }
}
