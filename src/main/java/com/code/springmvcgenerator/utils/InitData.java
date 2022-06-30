package com.code.springmvcgenerator.utils;

import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.service.EntityDetailService;
import com.code.springmvcgenerator.service.Vector3SService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final EntityDetailService entityDetailService;
    private final Vector3SService vector3SService;

    @Autowired
    public InitData(EntityDetailService entityDetailService, Vector3SService vector3SService) {
        this.entityDetailService = entityDetailService;
        this.vector3SService = vector3SService;
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
    }
}
