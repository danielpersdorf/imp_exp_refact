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
            oDocLocal.partner = new Partner("Updated on import");



            // get SQL from INI
            // replace placeholder in SQL by current business Data
            // query that sql on the DB
            // change the doc according to result of query

            // in reality this looks like this

            // schreibeLogUndConsole("CardCode Ermittelung");

//            Recordset rs = null;
//
//            // SQL zur Verarbeitung in sSQL stecken
//            string SQL = CardCodeErmitteln;
//
//            //get CardCode (= ExterneKundennummer im CardCode Feld)
//            string CardCode = Doc.CardCode;
//
//            // correct SQL statement
//            SQL = SQL.Replace("%CardCode%", CardCode);
//
//            // richtigen CardCode (und CardName) ermitteln
//            try
//            {
//                rs = DI_API.doRequest(SQL);
//            }
//            catch (Exception SQLex)
//            {
//                Program.mlogger.logger.Error("SQL Fehler: " + SQLex);
//            }
//
//            // CardCode und CardName überschreiben
//            Doc.CardCode = rs.Fields.Item(0).Value;
//            Doc.CardName = rs.Fields.Item(1).Value;
        }

        if (import_export.ini.get("ImportSettings", "ChangeItem").equals("Y")
                & import_export.ini.get("ImportSettings", "ChangeItemObjectTypes").contains(objNr) ) {

            
            // originally like this
//            schreibeLogUndConsole("Chargennummer aus MHD ermitteln");
//
//            string SQL;
//            string SQL_Artikel;
//            string ItemCode;
//            string ExpDate;
//
//            DateTime dtExpDate;
//
//            // SQL aus INI zur Verarbeitung in sSQL stecken
//            SQL = ChargennummerViaMHD;
//
//            // for jede Zeile des oDoc
//            for (int i = 0; i < Doc.Lines.Count; i++)
//            {
//                Doc.Lines.SetCurrentLine(i);
//
//                // get Values from XML
//                ItemCode = Doc.Lines.ItemCode;
//
//                // get ExpDate (= MHD im BTNT Feld)
//                // mussin XML in SBO Style sein -> 20211113
//                // 2021-06-30 (ISO Format) führt zu Server Error
//                //sExpDate = oDoc.Lines.BatchNumbers.ExpiryDate.ToString();
//                // -> 13.11.2021 00:00:000    mit Timestamp
//                dtExpDate = Doc.Lines.BatchNumbers.ExpiryDate;
//                //sExpDate = dtExpDate.Date.ToString();
//                // -> 13.11.2021 00:00:000   mit Timestamp
//                // -> 30.06.2021 00:00:000    mit Timestamp aber 0 (.06.) noch da
//
//                // nur das Datum / richtig zusammen setzen
//                //sExpDate = "" + dtExpDate.Year + "" + dtExpDate.Month + "" + dtExpDate.Day ;
//                // -> 20211113
//                // -> 2021630 !!! Vorsicht !!!  jetzt: fehlende 0
//                ExpDate = "" + dtExpDate.Year + "" + dtExpDate.Month.ToString("d2") + "" + dtExpDate.Day.ToString("d2");
//
//                // replace Values in SQL statement
//                SQL_Artikel = SQL.Replace("%ItemCode%", ItemCode);
//                SQL_Artikel = SQL_Artikel.Replace("%ExpDate%", ExpDate);
//
//                // richtige Chargennummer ermitteln
//                try
//                {
//                    rs = DI_API_request.doRequest(SQL_Artikel);
//                }
//                catch (Exception SQLex)
//                {
//                    Program.mlogger.logger.Error("SQL Fehler bei Chargennummer-Ermittlung: " + SQLex);
//                }
//
//                // Chargennummer der Zeile überschreiben
//                Doc.Lines.BatchNumbers.BatchNumber = rs.Fields.Item(0).Value;
        //}
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
