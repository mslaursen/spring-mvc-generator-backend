package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.ClassDetail;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ExportService {
    public void exportZipWithClasses(HttpServletResponse response, List<ClassDetail> classes, String folderPath) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());

        ArrayList<File> files = new ArrayList<>();
        for (ClassDetail c : classes) {
            String filePath = folderPath + "/" + c.getName() + ".java";
            File newFile = new File(filePath);
            if (newFile.createNewFile()) {
                FileWriter fw = new FileWriter(newFile);
                fw.write(c.getContent());
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
