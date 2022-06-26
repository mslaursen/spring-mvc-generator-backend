package com.code.springmvcgenerator.controller;

import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.service.EntityDetailService;
import com.code.springmvcgenerator.utils.Util;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/entity-details")
@CrossOrigin
public class EntityDetailController {

    private final EntityDetailService service;

    @Autowired
    public EntityDetailController(EntityDetailService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public void test(@RequestBody List<EntityDetail> eds, HttpServletResponse response) throws IOException {
        Util.clearFolder("files-test");

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());

        ArrayList<File> files = new ArrayList<>();
        for (EntityDetail ed : eds) {
            String filePath = "files-test/" + ed.getEntityName() + ".java";
            File newFile = new File(filePath);
            if (newFile.createNewFile()) {
                FileWriter fw = new FileWriter(newFile);
                fw.write(service.toEntityClass(ed));
                files.add(new File(newFile.getAbsolutePath()));
                fw.close();
            }
        }

        for (File file : files) {
            zos.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fis = new FileInputStream(file);

            IOUtils.copy(fis, zos);

            fis.close();
            zos.closeEntry();
        }
        zos.close();
    }
}
