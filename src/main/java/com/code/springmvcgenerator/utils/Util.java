package com.code.springmvcgenerator.utils;

import com.google.common.primitives.Chars;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.atteo.evo.inflector.English;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static String pluralize(String string) {
        String pluralized = English.plural(string);
        pluralized = decapitalize(pluralized);

        LinkedList<Character> characterList = new LinkedList<>(Chars.asList(pluralized.toCharArray()));

        for (int i = 0; i < characterList.size(); i++) {
            Character c = characterList.get(i);
            if (Character.isUpperCase(c)) {
                characterList.add(i, '-');
                i++;
            }
        }

        pluralized = characterList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining())
                .toLowerCase();

        return pluralized;
    }
}
