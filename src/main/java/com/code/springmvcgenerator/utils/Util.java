package com.code.springmvcgenerator.utils;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;

public class Util {
    public static void clearFolder(String path) throws IOException {
        File directory = new File(path);
        FileUtils.cleanDirectory(directory);
    }
}
