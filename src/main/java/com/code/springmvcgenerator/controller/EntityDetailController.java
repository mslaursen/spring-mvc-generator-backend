package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.service.EntityDetailService;
import com.code.springmvcgenerator.service.ZipService;
import com.code.springmvcgenerator.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/entity-details")
@CrossOrigin
public class EntityDetailController {

    private final EntityDetailService entityDetailService;
    private final ZipService zipService;

    @Autowired
    public EntityDetailController(EntityDetailService entityDetailService, ZipService zipService) {
        this.entityDetailService = entityDetailService;
        this.zipService = zipService;
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> download(@RequestBody List<EntityDetail> eds) throws FileNotFoundException {
        String filesFolder = "files-test";
        String zipName = "test.zip";

        zipService.zipEntities(eds, filesFolder, zipName);

        File zipFile = new File(zipName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));

        Util.clearFolder(filesFolder);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="  + zipFile.getName())
                .contentLength(zipFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
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

    @GetMapping("/{id}")
    public ResponseEntity<EntityDetail> fetchById(@PathVariable Long id) {
        EntityDetail foundEntityDetail = entityDetailService.findById(id);
        return ResponseEntity.ok()
                .body(foundEntityDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityDetail> updateById(@RequestBody EntityDetail entityDetail, @PathVariable Long id) {
        EntityDetail entityToUpdate = entityDetailService.findById(id);
        entityToUpdate.setName(entityDetail.getName());
        entityToUpdate.setNamePlural(entityDetail.getNamePlural());
        entityToUpdate.setHasCreate(entityDetail.getHasCreate());
        entityToUpdate.setHasRead(entityDetail.getHasRead());
        entityToUpdate.setHasUpdate(entityDetail.getHasUpdate());
        entityToUpdate.setHasDelete(entityDetail.getHasDelete());
        entityToUpdate.setRelations(entityDetail.getRelations());
        entityToUpdate.setVariables(entityDetail.getVariables());

        EntityDetail updatedEntity = entityDetailService.update(entityToUpdate);
        return ResponseEntity.ok()
                .body(updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        entityDetailService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
