package com.imp_exp.refact.basicModel;

import com.imp_exp.refact.tinyErpModel.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/** In Java static classes are not allowed here
 * */
public class Import {

    public static String objTyp;
    public static String objNr;

    private static BusinessService business = import_export.business;

    public static String iniSection;
    public static String iniTrigger;
    public static String iniImportPath;
    public static String iniImportArchivePath;
    public static String iniImportErrorPath;
    public static String tableName;

    // public static Document oDocLocal;

    /**
     * to decide which parts of the ini to read
     */
    public static void prepareImport() {
        switch (import_export.ObjNr) {
            case "2":
                objNr = import_export.ObjNr;
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
                objNr = import_export.ObjNr;
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
                objNr = import_export.ObjNr;
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
                objNr = import_export.ObjNr;
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
                objNr = import_export.ObjNr;
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
                objNr = import_export.ObjNr;
                objTyp = "None";
                iniSection = "Import";
                iniTrigger = "NoTrigger";
                iniImportPath = "AndNoPath";
                iniImportArchivePath = "AlsoNoArchivePath";
                iniImportErrorPath = "AndWithoutErrorPath";
                break;
        }
    }

    public static void doImport(String job)  {

        System.out.println("import started");

        switch (objNr) {
            //case "partner":
            case "2":
                // if (IniFileHelper.ReadValue(Section, ImportTrigger, FilePath, "") == "J")
                importPartner(job);
                break;
            //case "item":
            case "4":
                importItem(job);
                break;
            //case "document":
            default:
                importDocument(job);
                break;
        }
    }

    private static void importPartner(String filePath) {

        // set date and path strings
        String archive = import_export.ini.get(iniSection, iniImportArchivePath);
        String error = import_export.ini.get(iniSection, iniImportErrorPath);

        // string Datum = "_" + DateTime.Now.ToString("yyyy-MM-dd-HH-mm-ss-fff");
        String date = "_" + LocalDateTime.now();

        // NOTE:: this must have a side effect
        // business must memorize that oDoc too -> static oDocCompany
        Partner oPartnerLocal = business.getPartnerFromXML(filePath);

        try {
            if (oPartnerLocal.addPartnerToList()) {

                // benachrichtigeUserBeiErfolg();
                System.out.println("partner added");
                business.showPartners();

                // setzeErfolgVars(psDateiPfad);
                // setzeErfolgVarsProObjektTyp(oDocLocal);

                // sPfad = getPfad(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                int positionOfExtension = filePath.lastIndexOf(".");
                int positionOfSeperator = filePath.lastIndexOf("/");
                String fileNameExtension = filePath.substring(positionOfExtension);
                String fileName = filePath.substring(positionOfSeperator + 1, positionOfExtension );
                String archiveFileName = "src/main/java/com/imp_exp/refact/" + archive + fileName + date + fileNameExtension;

                // archiviereXmlDatei(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                Path fileToMovePath = Paths.get(filePath);
                Path targetPath = Paths.get(archiveFileName);
                Files.move(fileToMovePath, targetPath);

                // if (sollInboundPufferGeschriebenWerden)
                //inboundGeschrieben = insertInbound(oDocLocal);

            } else {
                // benachrichtigeUserImFehlerfall(psDateiPfad);
                business.showPartners();
                System.out.println("partner not added");

                // get error path
                int positionOfExtension = filePath.lastIndexOf(".");
                int positionOfSeperator = filePath.lastIndexOf("/");
                String fileNameExtension = filePath.substring(positionOfExtension);
                String fileName = filePath.substring(positionOfSeperator + 1, positionOfExtension );
                String errorFileName = "src/main/java/com/imp_exp/refact/" + error + fileName + date + fileNameExtension;

                // schreibeDateiInFehlerverzeichnis(psDateiPfad, fehler, Datum);
                Path fileToMovePath = Paths.get(filePath);
                Path targetPath = Paths.get(errorFileName);
                Files.move(fileToMovePath, targetPath);

                // setzeFehlerVars(fehler, Datum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            oPartnerLocal = null;
        }
    }

    private  static  void importItem(String filePath) {

        // set date and path strings
        String archive = import_export.ini.get(iniSection, iniImportArchivePath);
        String error = import_export.ini.get(iniSection, iniImportErrorPath);

        // string Datum = "_" + DateTime.Now.ToString("yyyy-MM-dd-HH-mm-ss-fff");
        String date = "_" + LocalDateTime.now();

        // NOTE:: this must have a side effect
        // business must memorize that oDoc too -> static oDocCompany
        Item oItemLocal = business.getItemFromXML(filePath);

        try {
            if (oItemLocal.addItemToList()) {

                // benachrichtigeUserBeiErfolg();
                System.out.println("item added");
                business.showItems();

                // sPfad = getPfad(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                int positionOfExtension = filePath.lastIndexOf(".");
                int positionOfSeperator = filePath.lastIndexOf("/");
                String fileNameExtension = filePath.substring(positionOfExtension);
                String fileName = filePath.substring(positionOfSeperator + 1, positionOfExtension );
                String archiveFileName = "src/main/java/com/imp_exp/refact/" + archive + fileName + date + fileNameExtension;

                // archiviereXmlDatei(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                Path fileToMovePath = Paths.get(filePath);
                Path targetPath = Paths.get(archiveFileName);
                Files.move(fileToMovePath, targetPath);

            } else {
                // benachrichtigeUserImFehlerfall(psDateiPfad);
                business.showPartners();
                System.out.println("partner not added");

                // get error path
                int positionOfExtension = filePath.lastIndexOf(".");
                int positionOfSeperator = filePath.lastIndexOf("/");
                String fileNameExtension = filePath.substring(positionOfExtension);
                String fileName = filePath.substring(positionOfSeperator + 1, positionOfExtension );
                String errorFileName = "src/main/java/com/imp_exp/refact/" + error + fileName + date + fileNameExtension;

                // schreibeDateiInFehlerverzeichnis(psDateiPfad, fehler, Datum);
                Path fileToMovePath = Paths.get(filePath);
                Path targetPath = Paths.get(errorFileName);
                Files.move(fileToMovePath, targetPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            oItemLocal = null;
        }
    }

    private  static  void importDocument(String filePath) {

        // set date and path strings
        String archive = import_export.ini.get(iniSection, iniImportArchivePath);
        String error = import_export.ini.get(iniSection, iniImportErrorPath);

        // string Datum = "_" + DateTime.Now.ToString("yyyy-MM-dd-HH-mm-ss-fff");
        String date = "_" + LocalDateTime.now();

        // NOTE:: this must have a side effect
        // business must memorize that oDoc too -> static oDocCompany
        Document oDocLocal = business.getDocumentFromXML(filePath);

        if (import_export.ini.get("ImportSettings", "ChangePartner").equals("Y")
                & import_export.ini.get("ImportSettings", "ChangePartnerObjectTypes").contains(objNr) ) {

            // NOTE::
            // the original had many of those,
            // this pre-import-manipulation worked like this:
            // * get SQL from INI (e.g. SELECT Partner_ID FROM Partner_TBL WHERE Partner_Name = '%Partner_Name_from_Program%')
            // * get business data and replace() placeholder in SQL by current business Data (e.g. Partner_Name)
            // * query that sql on the DB and get a resultSet
            // * change the doc according to result

            // our simplified version does just:
            System.out.println("Changing partner");
            oDocLocal.partner = new Partner("Updated Partner");
        }

        if (import_export.ini.get("ImportSettings", "ChangeItem").equals("Y")
                & import_export.ini.get("ImportSettings", "ChangeItemObjectTypes").contains(objNr) ) {

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
            oDocLocal.items.remove(2); 
            oDocLocal.items.add(new Item("Updated Item"));
        }

        try {
            if (oDocLocal.addDocumentToList()) {

                // benachrichtigeUserBeiErfolg();
                System.out.println("document added");
                business.showDocuments();

                // setzeErfolgVars(psDateiPfad);
                // setzeErfolgVarsProObjektTyp(oDocLocal);

                // sPfad = getPfad(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                int positionOfExtension = filePath.lastIndexOf(".");
                int positionOfSeperator = filePath.lastIndexOf("/");
                String fileNameExtension = filePath.substring(positionOfExtension);
                String fileName = filePath.substring(positionOfSeperator + 1, positionOfExtension );
                String archiveFileName = "src/main/java/com/imp_exp/refact/" + archive + fileName + date + fileNameExtension;

                // archiviereXmlDatei(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                Path fileToMovePath = Paths.get(filePath);
                Path targetPath = Paths.get(archiveFileName);
                Files.move(fileToMovePath, targetPath);

                // if (sollInboundPufferGeschriebenWerden)
                //inboundGeschrieben = insertInbound(oDocLocal);

            } else {
                // benachrichtigeUserImFehlerfall(psDateiPfad);
                business.showDocuments();
                System.out.println("Document not added");

                // get error path
                int positionOfExtension = filePath.lastIndexOf(".");
                int positionOfSeperator = filePath.lastIndexOf("/");
                String fileNameExtension = filePath.substring(positionOfExtension);
                String fileName = filePath.substring(positionOfSeperator + 1, positionOfExtension );
                String errorFileName = "src/main/java/com/imp_exp/refact/" + error + fileName + date + fileNameExtension;

                // schreibeDateiInFehlerverzeichnis(psDateiPfad, fehler, Datum);
                Path fileToMovePath = Paths.get(filePath);
                Path targetPath = Paths.get(errorFileName);
                Files.move(fileToMovePath, targetPath);

                // setzeFehlerVars(fehler, Datum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            oDocLocal = null;
        }
    }

}
