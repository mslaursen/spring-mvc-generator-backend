package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.ClassDetail;
import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.service.ClassDetailService;
import com.code.springmvcgenerator.service.EntityDetailService;
import com.code.springmvcgenerator.service.ExportService;
import com.code.springmvcgenerator.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("/api/entity-details")
@CrossOrigin
public class EntityDetailController {

    private final EntityDetailService entityDetailService;
    private final ClassDetailService classDetailService;
    private final ExportService exportService;

    @Autowired
    public EntityDetailController(EntityDetailService entityDetailService, ClassDetailService classDetailService, ExportService exportService) {
        this.entityDetailService = entityDetailService;
        this.classDetailService = classDetailService;
        this.exportService = exportService;
    }




    @PostMapping("/export")
    public void exportZip(@RequestBody List<EntityDetail> eds, HttpServletResponse response) {
        String zipFolder = "files-test";
        String zipName = "test.zip";

        Util.clearFolder(zipFolder);

        List<ClassDetail> classes = classDetailService.getAllClasses(eds);
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"" + zipName + "\"");

        exportService.exportZipWithClasses(response, classes, zipFolder);
    }

    @PostMapping
    public ResponseEntity<EntityDetail> create(@RequestBody EntityDetail entityDetail) {
        EntityDetail savedEntityDetail = entityDetailService.save(entityDetail);
        return ResponseEntity.ok()
                .body(savedEntityDetail);
    }

    @GetMapping
    public ResponseEntity<List<EntityDetail>> fetchEntityDetails() {
        List<EntityDetail> entityDetails = entityDetailService.findAll();
        return ResponseEntity.ok()
                .body(entityDetails);
    }
}
