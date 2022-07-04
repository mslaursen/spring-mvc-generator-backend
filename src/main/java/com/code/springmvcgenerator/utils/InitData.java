package com.code.springmvcgenerator.utils;

import com.code.springmvcgenerator.entity.*;
import com.code.springmvcgenerator.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final EntityService entityService;
    private final RelationService relationService;
    private final VariableService variableService;
    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public InitData(EntityService entityService, RelationService relationService, VariableService variableService, ProjectService projectService, UserService userService) {
        this.entityService = entityService;
        this.relationService = relationService;
        this.variableService = variableService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        User user = new User();
        user.setEmail("a");
        user.setPassword("b");
        userService.save(user);

        Project project = new Project();
        project.setName("Test");
        project.setUser(user);

        projectService.create(project);


        Entity entity = new Entity();
        entity.setName("City");

        entity.setHasCreate(true);
        entity.setHasReadAll(true);
        entity.setHasRead(true);
        entity.setHasUpdate(false);
        entity.setHasDelete(false);
        entity.setProject(project);
        entityService.save(entity);

        Variable v1 = new Variable();
        v1.setDataType("Integer");
        v1.setName("AgeLimit");
        v1.setColumnName("age_limit");
        v1.setEntity(entity);
        variableService.save(v1);

        Relation r1 = new Relation();
        r1.setAnnotation("OneToMany");
        r1.setRelatedTo("City");
        r1.setEntity(entity);
        relationService.save(r1);

        Relation r2 = new Relation();
        r2.setAnnotation("ManyToOne");
        r2.setRelatedTo("Man");
        r2.setEntity(entity);
        relationService.save(r2);

        Entity entity2 = new Entity();
        entity2.setName("City2");
        entity2.setHasCreate(true);
        entity2.setHasReadAll(true);
        entity2.setHasRead(true);
        entity2.setHasUpdate(false);
        entity2.setHasDelete(false);
        entity2.setProject(project);
        entityService.save(entity2);
    }
}
