package com.imp_exp.refact.basicModel;

import junitparams.JUnitParamsRunner;
import org.ini4j.Ini;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
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

        Ini ini = new Ini(new File("src/main/java/com/imp_exp/refact/basicModel/imp_exp.ini"));

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



}