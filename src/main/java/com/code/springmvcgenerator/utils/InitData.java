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
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123321");
        userService.save(user);

    }
}
