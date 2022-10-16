package org.mattpayne.simple.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


// From https://stackoverflow.com/questions/44399422/read-file-from-resources-folder-in-spring-boot on 2022-Oct-15
public class UtilsFromStackOverflow {
    public static String getResourceFileAsString(String fileName) {
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return (String)reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
    }

    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = UtilsFromStackOverflow.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

}
