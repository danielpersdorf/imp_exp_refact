package com.imp_exp.refact.smallXmpls;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** some examples from bealdung.com */
public class MovingFilesTest {
    private final String FILE_TO_MOVE = "src/test/resources/originalFileToMove.txt";
    private final String TARGET_FILE = "src/test/resources/targetFileToMove.txt";

    @BeforeEach
    public void createFileToMove() throws IOException {
        File fileToMove = new File(FILE_TO_MOVE);
        fileToMove.createNewFile();
    }

    @AfterEach
    public void cleanUpFiles() {
        File targetFile = new File(TARGET_FILE);
        targetFile.delete();
    }

    /** this is the way original import_export did it */
    @Test
    public void givenUsingNio_whenMovingFile_thenCorrect() throws IOException {
        Path fileToMovePath = Paths.get(FILE_TO_MOVE);
        Path targetPath = Paths.get(TARGET_FILE);
        Files.move(fileToMovePath, targetPath);
    }

    @Test
    public void givenUsingFileClass_whenMovingFile_thenCorrect() throws IOException {
        File fileToMove = new File(FILE_TO_MOVE);
        boolean isMoved = fileToMove.renameTo(new File(TARGET_FILE));
        if (!isMoved) {
            throw new FileSystemException(TARGET_FILE);
        }
    }
}
