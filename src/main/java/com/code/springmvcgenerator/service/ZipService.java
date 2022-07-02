package com.code.springmvcgenerator.service;

import com.code.springmvcgenerator.entity.ClassDetail;
import com.code.springmvcgenerator.entity.EntityDetail;
import com.code.springmvcgenerator.utils.Util;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipService {

    private final ClassDetailService classDetailService;

    @Autowired
    public ZipService(ClassDetailService classDetailService) {
        this.classDetailService = classDetailService;
    }

    public void zipEntities(List<EntityDetail> eds, String filesFolder, String zipName) {
        Util.clearFolder(filesFolder);

        // Create java class files for each entityDetail
        List<File> files = createEntityFiles(eds, filesFolder);


        // Add files to zip
        zipFiles(files, zipName);
    }

    private void zipFiles(List<File> files, String zipName) {
        try {
            FileOutputStream fos = new FileOutputStream(zipName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            zipOut.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<File> createEntityFiles(List<EntityDetail> eds, String fileFolder) {
        ArrayList<File> files = new ArrayList<>();
        List<ClassDetail> classes = classDetailService.getAllClasses(eds);

        try {

            for (ClassDetail c : classes) {
                String filePath = fileFolder + "/" + c.getName() + ".java";
                File newFile = new File(filePath);

                if (newFile.createNewFile()) {
                    FileWriter fw = new FileWriter(newFile);
                    fw.write(c.getContent());
                    files.add(new File(newFile.getAbsolutePath()));
                    fw.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
