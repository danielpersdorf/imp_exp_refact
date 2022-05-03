package com.imp_exp.refact.manyClasses;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class FileHandler {

    Configuration config;

    public FileHandler() {
        this.config = import_export.config;
    }
    public FileHandler(Configuration config) {
        this.config = config;
    }

    /** delete xml files from /archive
     * checks the Directories given in the param and deletes old files if necessary
     * The directories which you want to check for old files */
    public void deleteOldArchiveFiles(String[] directories) {
        for (String dir : directories) {
            if (dir == "") { continue; }
            deleteOldArchiveFiles(dir);
        }
    }

    /** delete xml files from /archive
     * checks the Directories given in the param and deletes old files if necessary
     * The directories which you want to check for old files */
    public void deleteOldArchiveFiles(String directory) {

        if (directory == "") { return; }

        // int archiveDuration = parseInt(import_export.ini.get("Settings","ArchiveDuration"));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fileTime;

        List<String> deleteFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {

                    String fileName = path.toString();
                    File file = new File(fileName);

                    BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    fileTime = LocalDateTime.parse(attr.creationTime().toString().replace("Z", ""));

                    // check if difference between now and the age of the file is greater than the maximum allowed age
                    if (Duration.between(fileTime, now).toHours() >= config.archiveDuration) {
                        deleteFiles.add(fileName);
                    }
                }
            }

            for (String file : deleteFiles) {
                Files.delete(Paths.get(file));
                System.out.println("Archive-file : " + file + " has been deleted because it is older than " + config.archiveDuration + " hours.");
            }
        }
        catch (Exception ex) {
            System.out.println("Error on deleting an old file from archive : " + ex.getStackTrace());
        }
    }
}

