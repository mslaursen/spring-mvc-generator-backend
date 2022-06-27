package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.ClassDetail;
import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.service.EntityDetailService;
import com.code.springmvcgenerator.service.ExportService;
import com.code.springmvcgenerator.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    public void exportZip(@RequestBody List<EntityDetail> eds, HttpServletResponse response) {
        String zipFolder = "files-test";
        String zipName = "test.zip";

        Util.clearFolder(zipFolder);

        List<ClassDetail> classes = entityDetailService.getAllClasses(eds);
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"" + zipName + "\"");

        exportService.exportZipWithClasses(response, classes, zipFolder);
    }
}
