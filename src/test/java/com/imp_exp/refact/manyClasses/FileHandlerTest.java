package com.imp_exp.refact.manyClasses;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class FileHandlerTest {

    static FileHandler fileHandler;

    @BeforeAll
    static void beforeAll() throws IOException {
        Configuration config = new Configuration();
        config.archiveDuration = 1;
        fileHandler = new FileHandler(config);
    }

    @Test
    void test_deleteOldArchiveFiles_fromSingleDirectory() {
        fileHandler.deleteOldArchiveFiles(
                "src/main/java/com/imp_exp/refact/externalData/partners/archive/");
    }

    @Test
    void test_deleteOldArchiveFiles_fromMultipleDirectories() {
        String[] directories = new String[] {
                "src/main/java/com/imp_exp/refact/externalData/items/archive/",
                "src/main/java/com/imp_exp/refact/externalData/documents/orders/archive/"};
        fileHandler.deleteOldArchiveFiles(directories);
    }
}