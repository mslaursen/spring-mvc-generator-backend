package com.code.springmvcgenerator.utils;

import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.entity.Variable;
import com.code.springmvcgenerator.service.EntityDetailService;
import com.code.springmvcgenerator.service.RelationService;
import com.code.springmvcgenerator.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final EntityDetailService entityDetailService;
    private final RelationService relationService;
    private final VariableService variableService;

    @Autowired
    public InitData(EntityDetailService entityDetailService, RelationService relationService, VariableService variableService) {
        this.entityDetailService = entityDetailService;
        this.relationService = relationService;
        this.variableService = variableService;
    }




    @Override
    public void run(String... args) {
        EntityDetail entityDetail = new EntityDetail();
        entityDetail.setEntityName("City");
        entityDetail.setEntityNamePlural("cities");
        entityDetail.setHasCreate(true);
        entityDetail.setHasRead(true);
        entityDetail.setHasUpdate(false);
        entityDetail.setHasDelete(false);
        entityDetailService.save(entityDetail);

        Variable v1 = new Variable();
        v1.setDataType("Integer");
        v1.setName("AgeLimit");
        v1.setNamePlural("age_limit");
        v1.setEntityDetail(entityDetail);
        variableService.save(v1);

        EntityDetail entityDetail2 = new EntityDetail();
        entityDetail2.setEntityName("City2");
        entityDetail2.setEntityNamePlural("cities");
        entityDetail2.setHasCreate(true);
        entityDetail2.setHasRead(true);
        entityDetail2.setHasUpdate(false);
        entityDetail2.setHasDelete(false);
        entityDetailService.save(entityDetail2);
    }
}
