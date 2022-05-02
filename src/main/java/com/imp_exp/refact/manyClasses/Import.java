package com.imp_exp.refact.manyClasses;

import com.imp_exp.refact.tinyErpModel.BusinessService;
import com.imp_exp.refact.tinyErpModel.Document;
import com.imp_exp.refact.tinyErpModel.Item;
import com.imp_exp.refact.tinyErpModel.Partner;

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
import java.util.List;

import static java.lang.Integer.parseInt;


public class Import {

    private static BusinessService business = import_export.business;
    private static Configuration config = import_export.config;

    private static FileHandler fileHandler = new FileHandler();

    public static String objTyp;
    public static String objNr;
    public static String iniSection;
    public static String iniTrigger;
    public static String iniImportPath;
    public static String iniImportArchivePath;
    public static String iniImportErrorPath;
    public static String tableName;


    /** to decide which parts of the ini to read */
    public static void prepareImport() {
        switch (import_export.objNr) {
            case "2":
                objNr = import_export.objNr;
                objTyp = "partner";
                iniSection = "Import";
                iniTrigger = "Partner";
                iniImportPath = "PartnerPath";
                iniImportArchivePath = "PartnerArchivePath";
                iniImportErrorPath = "PartnerErrorPath";
                // tableName = "OCRD";
                // TODO:: object type
                break;

            case "4":
                objNr = import_export.objNr;
                objTyp = "item";
                iniSection = "Import";
                iniTrigger = "Item";
                iniImportPath = "ItemPath";
                iniImportArchivePath = "ItemArchivePath";
                iniImportErrorPath = "ItemErrorPath";
                // tableName = "OITM";
                // Document oDoc = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oItems);
                break;
            case "17":
                objNr = import_export.objNr;
                objTyp = "Order";
                iniSection = "Import";
                iniTrigger = "Order";
                iniImportPath = "OrderPath";
                iniImportArchivePath = "OrderArchivePath";
                iniImportErrorPath = "OrderErrorPath";
                // tableName = "ORDR";
                // Document oDoc = Program.oCompany.GetBusinessObject(BoObjectTypes.oOrders);
                break;

            case "13":
                objNr = import_export.objNr;
                objTyp = "Invoice";
                iniSection = "Import";
                iniTrigger = "Invoice";
                iniImportPath = "InvoicePath";
                iniImportArchivePath = "InvoiceArchivePath";
                iniImportErrorPath = "InvoiceErrorPath";
                // tableName = "OINV";
                // Document oDoc = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oInvoices);
                break;

            case "15":
                objNr = import_export.objNr;
                objTyp = "Delivery";
                iniSection = "Import";
                iniTrigger = "Delivery";
                iniImportPath = "DeliveryPath";
                iniImportArchivePath = "DeliveryArchivePath";
                iniImportErrorPath = "DeliveryErrorPath";
                // tableName = "ODLN";
                // Document oDoc = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oDeliveryNotes);
                break;
            /*
            Many other types following
            {...}
            */
            default:
                objNr = import_export.objNr;
                objTyp = "None";
                iniSection = "Import";
                iniTrigger = "NoTrigger";
                iniImportPath = "AndNoPath";
                iniImportArchivePath = "AlsoNoArchivePath";
                iniImportErrorPath = "AndWithoutErrorPath";
                break;
        }
    }

    public static void doImport(String job) {

        System.out.println("import started");

        switch (objNr) {
            case "2":
                importPartner(job);
                break;
            case "4":
                importItem(job);
                break;
            default:
                importDocument(job);
                break;
        }
    }

    private static void importPartner(String filePath) {

        String date = getDateString();
        String archive = import_export.ini.get(iniSection, iniImportArchivePath);
        String error = import_export.ini.get(iniSection, iniImportErrorPath);

        // NOTE:: this must have a side effect
        // business must memorize that oDoc too -> static oDocCompany
        Partner oPartnerLocal = business.getPartnerFromXML(filePath);

        try {
            if (oPartnerLocal.addPartnerToList()) {
                logImportSuccess("Partner");
                business.showPartners();
                moveXml(filePath, getTargetFileName(filePath, archive, date));
            } else {
                // benachrichtigeUserImFehlerfall(psDateiPfad);
                business.showPartners();
                logImportFailure("Partner");
                moveXml(filePath, getTargetFileName(filePath, error, date));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            oPartnerLocal = null;
        }
    }

    private  static  void importItem(String filePath) {

        String date = getDateString();
        String archive = import_export.ini.get(iniSection, iniImportArchivePath);
        String error = import_export.ini.get(iniSection, iniImportErrorPath);

        Item oItemLocal = business.getItemFromXML(filePath);

        try {
            if (oItemLocal.addItemToList()) {
                logImportSuccess(objTyp);
                business.showItems();
                moveXml(filePath, getTargetFileName(filePath, archive, date));
            } else {
                business.showPartners();
                logImportFailure(objTyp);
                moveXml(filePath, getTargetFileName(filePath, error, date));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            oItemLocal = null;
        }
    }

    private  static  void importDocument(String filePath) {

        String date = getDateString();
        String archive = import_export.ini.get(iniSection, iniImportArchivePath);
        String error = import_export.ini.get(iniSection, iniImportErrorPath);

        Document oDocLocal = business.getDocumentFromXML(filePath);

        if (isPartnerChangeActive()) oDocLocal = changePartnerOn(oDocLocal);

        if (isItemChangeActive()) oDocLocal = changeItemOn(oDocLocal);

        try {
            if (oDocLocal.addDocumentToList()) {
                logImportSuccess(objTyp);
                business.showDocuments();
                moveXml(filePath, getTargetFileName(filePath, archive, date));
            } else {
                business.showDocuments();
                logImportFailure(objTyp);
                moveXml(filePath, getTargetFileName(filePath, error, date));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            oDocLocal = null;
        }
    }

    private static String getDateString() {
        String date = "_" + LocalDateTime.now();
        return  date.replace(":", "-");
    }

    private static Boolean isPartnerChangeActive() {
        return config.isPartnerChangeActive & config.partnerChangeTypes.contains(objNr);
    }

    private static Document changePartnerOn(Document docLocal) {
        // NOTE::
        // the original had many of those,
        // this pre-import-manipulation worked like this:
        // * get SQL from INI (e.g. SELECT Partner_ID FROM Partner_TBL WHERE Partner_Name = '%Partner_Name_from_Program%')
        // * get business data and replace() placeholder in SQL by current business Data (e.g. Partner_Name)
        // * query that sql on the DB and get a resultSet
        // * change the doc according to result

        // our simplified version does just:
        System.out.println("Changing partner");
        docLocal.partner = new Partner("Updated Partner");
        return docLocal;
    }

    private static Boolean isItemChangeActive() {
        return config.isItemChangeActive & config.itemChangeTypes.contains(objNr);
    }

    private static Document changeItemOn(Document docLocal) {
        // NOTE::
        // the original had many of those,
        // another pre-import-manipulation worked like this:
        // * get SQL from INI (e.g. SELECT Item_ID FROM Item_TBL WHERE Item_Property = '%Item_Property_in_question%')
        // * for items, you need to loop through the list of items
        // * -> get data value from the imported xml line of a certain item
        // * replace() placeholder in SQL by that current data value (e.g. Item_Property_in_question)
        // * query that sql on the DB and get a resultSet
        // * change the doc according to result

        // our simplified version does just:
        System.out.println("Changing item");
        docLocal.items.remove(2);
        docLocal.items.add(new Item("Updated Item"));
        return docLocal;
    }

    private static void logImportSuccess(String type) {
        System.out.println(type + " added");
    }
    private static void logImportFailure(String type) {
        System.out.println(type + " not added");
    }

    private static String getTargetFileName(String filePath, String target, String date) {
        int positionOfExtension = filePath.lastIndexOf(".");
        int positionOfSeparator = filePath.lastIndexOf("/");
        String fileNameExtension = filePath.substring(positionOfExtension);
        String fileName = filePath.substring(positionOfSeparator + 1, positionOfExtension );
        return "src/main/java/com/imp_exp/refact/" + target + fileName + date + fileNameExtension;
    }
    private static void moveXml(String filePath, String targetFileName) throws IOException {
        Path fileToMovePath = Paths.get(filePath);
        Path targetPath = Paths.get(targetFileName);
        Files.move(fileToMovePath, targetPath);
    }


    // TODO:: wenn die public sind sollten die vielleicht in eine globale Util Klasse

    /** delete xml files from /archive
     * checks the Directories given in the param and deletes old files if necessary
     * The directories which you want to check for old files */
    public static void deleteOldArchiveFiles(String[] directories) {
        for (String dir : directories) {
            if (dir == "") { continue; }
            deleteOldArchiveFiles(dir);
        }
    }

    /** delete xml files from /archive
     * checks the Directories given in the param and deletes old files if necessary
     * The directories which you want to check for old files */
    public static void deleteOldArchiveFiles(String directory) {

        if (directory == "") { return; }

        int archiveDuration = parseInt(import_export.ini.get("Settings","ArchiveDuration"));
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
                    if (Duration.between(fileTime, now).toHours() >= archiveDuration) {
                        deleteFiles.add(fileName);
                    }
                }
            }

            for (String file : deleteFiles) {
                Files.delete(Paths.get(file));
                System.out.println("Archive-file : " + file + " has been deleted because it is older than " + archiveDuration + " hours.");
            }
        }
        catch (Exception ex) {
            System.out.println("Error on deleting an old file from archive : " + ex.getStackTrace());
        }
    }
}


