package com.code.springmvcgenerator.utils;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;

public class Util {

    public static void clearFolder(String path) {
        try {
            File directory = new File(path);
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        char[] c = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);

        return new String(c);
    }

    public static String capitalize(String string) {
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
