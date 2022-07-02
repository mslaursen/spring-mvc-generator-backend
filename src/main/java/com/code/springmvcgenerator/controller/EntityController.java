package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.Entity;
import com.code.springmvcgenerator.service.EntityService;
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
public class EntityController {

    private final EntityService entityService;
    private final ZipService zipService;

    @Autowired
    public EntityController(EntityService entityService, ZipService zipService) {
        this.entityService = entityService;
        this.zipService = zipService;
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> download(@RequestBody List<Entity> eds) throws FileNotFoundException {
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
    public ResponseEntity<Entity> create(@RequestBody Entity entity) {
        Entity savedEntity = entityService.save(entity);
        return ResponseEntity.ok()
                .body(savedEntity);
    }

    @GetMapping
    public ResponseEntity<List<Entity>> fetchEntityDetails() {
        List<Entity> entities = entityService.findAll();
        return ResponseEntity.ok()
                .body(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entity> fetchById(@PathVariable Long id) {
        Entity foundEntity = entityService.findById(id);
        return ResponseEntity.ok()
                .body(foundEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entity> updateById(@RequestBody Entity entity, @PathVariable Long id) {
        System.out.println(id);
        Entity entityToUpdate = entityService.findById(id);
        entityToUpdate.setName(entity.getName());
        entityToUpdate.setNamePlural(entity.getNamePlural());
        entityToUpdate.setHasCreate(entity.getHasCreate());
        entityToUpdate.setHasRead(entity.getHasRead());
        entityToUpdate.setHasUpdate(entity.getHasUpdate());
        entityToUpdate.setHasDelete(entity.getHasDelete());
        entityToUpdate.setRelations(entity.getRelations());
        entityToUpdate.setVariables(entity.getVariables());

        Entity updatedEntity = entityService.update(entityToUpdate);
        return ResponseEntity.ok()
                .body(updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        entityService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
