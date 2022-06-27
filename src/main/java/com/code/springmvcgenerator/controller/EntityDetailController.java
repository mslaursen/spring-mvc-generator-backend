package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.ClassDetail;
import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.service.EntityDetailService;
import com.code.springmvcgenerator.service.ExportService;
import com.code.springmvcgenerator.utils.Util;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/entity-details")
@CrossOrigin
public class EntityDetailController {

    private final EntityDetailService entityDetailService;
    private final ExportService exportService;

    @Autowired
    public EntityDetailController(EntityDetailService entityDetailService, ExportService exportService) {
        this.entityDetailService = entityDetailService;
        this.exportService = exportService;
    }

    @GetMapping("/export")
    public void getZip(@RequestBody List<EntityDetail> eds, HttpServletResponse response) throws IOException {
        Util.clearFolder("files-test");

        List<ClassDetail> classes = entityDetailService.getAllClasses(eds);
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        exportService.exportZipWithClasses(response, classes, "files-test");
    }
}
