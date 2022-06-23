package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.EntityDetail;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/entity-details")
@CrossOrigin
public class EntityDetailController {

    @PostMapping
    public ResponseEntity<Resource> test(@RequestBody EntityDetail ed) {


        System.out.println(ed.toClassFormat());


        Path path = Paths.get("files/test.java");
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert resource != null;
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
