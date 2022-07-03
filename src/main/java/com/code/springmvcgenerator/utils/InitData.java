package com.code.springmvcgenerator.utils;

import com.code.springmvcgenerator.entity.Entity;
import com.code.springmvcgenerator.entity.Variable;
import com.code.springmvcgenerator.service.EntityService;
import com.code.springmvcgenerator.service.RelationService;
import com.code.springmvcgenerator.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final EntityService entityService;
    private final RelationService relationService;
    private final VariableService variableService;

    @Autowired
    public InitData(EntityService entityService, RelationService relationService, VariableService variableService) {
        this.entityService = entityService;
        this.relationService = relationService;
        this.variableService = variableService;
    }

    @Override
    public void run(String... args) {
        Entity entity = new Entity();
        entity.setName("City");

        entity.setHasCreate(true);
        entity.setHasRead(true);
        entity.setHasUpdate(false);
        entity.setHasDelete(false);
        entityService.save(entity);

        Variable v1 = new Variable();
        v1.setDataType("Integer");
        v1.setName("AgeLimit");
        v1.setColumnName("age_limit");
        v1.setEntity(entity);
        variableService.save(v1);

        Entity entity2 = new Entity();
        entity2.setName("City2");
        entity2.setHasCreate(true);
        entity2.setHasRead(true);
        entity2.setHasUpdate(false);
        entity2.setHasDelete(false);
        entityService.save(entity2);
    }
}
