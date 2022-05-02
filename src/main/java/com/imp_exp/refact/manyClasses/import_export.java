package com.imp_exp.refact.manyClasses;

import com.imp_exp.refact.ImpExpBasicModelApplication;
import com.imp_exp.refact.tinyErpModel.BusinessService;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


public class import_export {

    private static Boolean componentsInitialised;
    private static Boolean foundJobsOnDB = false;

    public static BusinessService business;
    public static Configuration config;
    public static Ini ini;
    public static String objNr;

    public static void main(String[] args) {
        System.out.println("Started imp_exp ");
        componentsInitialised = initComponents();
        if (componentsInitialised) {
            doWork();
        }
    }

    private static Boolean initComponents() {
        Boolean iniInitialized = initIni();
        Boolean configInitialized = initConfig();
        Boolean loggerInitialized = initLogger();
        Boolean connectionInitialized = initConnection();

        if (iniInitialized & configInitialized & loggerInitialized & connectionInitialized) {
            System.out.println("initialized components ");
            return true;
        }
        return false;
    }
    private static Boolean initIni() {
        try {
            ini = new Ini(new File("src/main/java/com/imp_exp/refact/manyClasses/imp_exp.ini"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("found ini ");
        return true;
    }

    private static Boolean initConfig() {
        config = new Configuration();
        return true;
    }

    private static Boolean initLogger() {
        System.out.println("started log ");
        return true;
    }
    private static Boolean initConnection() {
        if (ImpExpBasicModelApplication.business != null) {
            business = ImpExpBasicModelApplication.business;
        } else {
            try {
                business = new BusinessService();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("connected to business ");
        return true;
    }
    private static Boolean allComponentsInitialized(Boolean ini, Boolean logger, Boolean conn) {
        return ini & logger & conn;
    }

    private static void doWork() {
        System.out.println("imp_exp start working ");
        do {
            doImports();
            doExports();
            doUpdates();
            doFillUdts();
        } while (!getProgramStop());
    }
    private static void doImports() {
        System.out.println("imports");

        String[] tmp = { "2", "4", "13", "15", "17", "18", "19", "20", "22", "63" };
        for(String x : tmp) {

            objNr = x;
            Import.prepareImport();

            if (Objects.equals(ini.get(Import.iniSection, Import.iniTrigger), "Y")) {
                System.out.println("found ini trigger " + Import.iniTrigger);

                String fullPathToFile = "src/main/java/com/imp_exp/refact/" + ini.get(Import.iniSection, Import.iniImportPath);
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(fullPathToFile))) {
                    for (Path path : stream) {
                        if (!Files.isDirectory(path)) {
                            String job = path.getFileName().toString();
                            if (job.contains(".xml")) {
                                String fullPathToJob = fullPathToFile + job;
                                System.out.println(fullPathToJob);
                                Import.doImport(fullPathToJob);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static void doExports() {
        System.out.println("exports");
        /* get jobs from DB via BusinessService */
        // List<Job> jobs = business.getJobsFrom(connection);
        // for (Job job : jobs)
        if (foundJobsOnDB) {
            switch ("job.Item1")
            {
                case "2":
                    // Customers (GP) / Primary Key: CardCode / Object Type = 2
                    objNr = "2";
                    // Export.exportPartner(job.Item2);
                    Export.exportPartner(11);
                    break;
                    /*
                    case "4":
                        // Items , Primary Key: ItemCode, Object Type: 4
                        ObjNr = "4";
                        Export.exportArtikel(job.Item2);
                        break;

                    case "13":
                        // A/R Invoice, Primary Key: DocEntry, Object Type: 13
                        ObjNr = "13";
                        Export.export(Int32.Parse(job.Item2));
                        break;

                    case "15":
                        // Delivery Note , Primary Key: DocEntry, Object Type: 15
                        ObjNr = "15";
                        Export.export(Int32.Parse(job.Item2));
                        break;

                    case "17":
                        // Order, Primary Key: DocEntry, Object Type: 17
                        ObjNr = "17";
                        Export.export(Int32.Parse(job.Item2));
                        break;

                    case "63":
                        // Prime key: ProjectCode, Object Type: 63
                        ObjNr = "63";
                        Export.exportProjekte((job.Item2));
                        break;
                        */
                default:
                    break;
            }
        }
    }
    private static void doUpdates() { System.out.println("started update"); }
    private static void doFillUdts() { System.out.println("started fillUdt"); }

    private static Boolean getProgramStop() { return ini.get("Settings", "ProgramStop").equals("Y"); }
}
