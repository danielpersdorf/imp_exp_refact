package com.imp_exp.refact.firstCleanup;

import junitparams.JUnitParamsRunner;
import org.ini4j.Ini;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitParamsRunner.class)
class import_exportTest {

    @ParameterizedTest
    @CsvSource({
            "Import,Partner,Y",
            "Import,Item,Y",
            "Export,PartnerPath,externalData/partners/",
            "Export,Partner,N" })
    void test_readFromIni(String section, String key, String expected) throws IOException {

        Ini ini = new Ini(new File("src/main/java/com/imp_exp/refact/firstCleanup/imp_exp.ini"));

        assertEquals(expected, ini.get(section, key));
    }

    @ParameterizedTest
    @CsvSource({
            "src/main/java/com/imp_exp/refact/externalData/partners/",
            "src/main/java/com/imp_exp/refact/externalData/items/",
            "src/main/java/com/imp_exp/refact/externalData/documents/orders/",
            "src/main/java/com/imp_exp/refact/externalData/documents/deliveries/",
            "src/main/java/com/imp_exp/refact/externalData/documents/invoices/",
    })
    void test_filesFromDirectory(String importPath) throws IOException {
        Set<String> filesFromDir = listFilesUsingDirectoryStream(importPath);
        for (String file : filesFromDir) {
            System.out.println(file);
        }
    }

    public Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }
        return fileList;
    }

    @ParameterizedTest
    @CsvSource({
            "src/main/java/com/imp_exp/refact/externalData/partners/",
            "src/main/java/com/imp_exp/refact/externalData/items/",
            "src/main/java/com/imp_exp/refact/externalData/documents/orders/",
            "src/main/java/com/imp_exp/refact/externalData/documents/deliveries/",
            "src/main/java/com/imp_exp/refact/externalData/documents/invoices/",
    })
    public void test_getFilesFromDir(String dir) throws IOException {
        List<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    String filename = path.getFileName().toString();
                    fileList.add(filename);
                    System.out.println(filename);
                }
            }
        }
        // return fileList;
        Assert.assertNotNull(fileList);
    }

    @Test
    public void test_fileTimeTesting() throws IOException {

        String directory = "src/main/java/com/imp_exp/refact/externalData/documents/orders/archive";

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fileTime;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {

                    String fileName = path.toString();
                    File file = new File(fileName);

                    BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

                    System.out.println("creationTime    : " + attr.creationTime());
                    System.out.println("lastAccessTime  : " + attr.lastAccessTime());
                    System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

                    System.out.println("and this is now : " + now.toString());

                    // ->
                    // creationTime    : 2022-04-15T06:03:03Z
                    // lastAccessTime  : 2022-04-15T06:03:10Z
                    // lastModifiedTime: 2022-04-15T06:03:03Z
                    // and this is now : 2022-04-15T10:14:31.618

                    fileTime = LocalDateTime.parse(attr.creationTime().toString().replace("Z", ""));
                    System.out.println("fileTime as LDT : " + fileTime);
                    // ->
                    // fileTime as LDT : 2022-04-15T06:03:03
                    // and this is now : 2022-04-15T10:14:31.618

                    Duration diff = Duration.between(fileTime, now);

                    // days between datetime 1 and datetime 2
                    System.out.println("Days : " + diff.toDays());

                    // hours between datetime 1 and datetime 2
                    System.out.println("Hours: " + diff.toHours());

                }
            }
        }
    }
}