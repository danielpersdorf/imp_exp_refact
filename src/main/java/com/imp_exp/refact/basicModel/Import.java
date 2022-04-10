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

        // wenn es ein Geschäftspartner ist, der importiert werden soll, so muss ein anderes Objekt erzeugt werden.
        // BusinessPartners oDocLocal = DI_API_request.connection.company.GetBusinessObjectFromXML(psDateiPfad, 0);

        try {

            if (business.importPartner(filePath)) {
                System.out.println("partner added");
                business.showPartners();

                // setzeErfolgVars(psDateiPfad);
                // setzeErfolgVarsProObjektTyp(oDocLocal);
                // benachrichtigeUserBeiErfolg();

                // sIPF_Pfad = getIPF_Pfad(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                int positionOfExtension = filePath.lastIndexOf(".");
                int positionOfSeperator = filePath.lastIndexOf("/");
                String fileNameExtension = filePath.substring(positionOfExtension);
                String fileName = filePath.substring(positionOfSeperator + 1, positionOfExtension );
                String archiveFileName = archive + fileName + date + fileNameExtension;

                // archiviereXmlDatei(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                Path fileToMovePath = Paths.get(filePath);
                Path targetPath = Paths.get(archiveFileName);
                Files.move(fileToMovePath, targetPath);

                // if (sollInboundPufferGeschriebenWerden)
                //inboundGeschrieben = insertInbound(oDocLocal);

            } else {
                // benachrichtigeUserImFehlerfall(psDateiPfad);
                // schreibeDateiInFehlerverzeichnis(psDateiPfad, fehler, Datum);
                // setzeFehlerVars(fehler, Datum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            // partner = null;
        }
    }

    private  static  void importDocument(String filePath) {

        try {
            if (business.importDocument(filePath)) {
                System.out.println("document added");
                business.showDocuments();

                // setzeErfolgVars(psDateiPfad);
                // setzeErfolgVarsProObjektTyp(oDocLocal);
                // sIPF_Pfad = getIPF_Pfad(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
                // benachrichtigeUserBeiErfolg();
                // archiviereXmlDatei(psDateiPfad, archiv, Datum, oDocLocal.CardCode);

                // if (sollInboundPufferGeschriebenWerden)
                //inboundGeschrieben = insertInbound(oDocLocal);

            } else {
                // benachrichtigeUserImFehlerfall(psDateiPfad);
                // schreibeDateiInFehlerverzeichnis(psDateiPfad, fehler, Datum);
                // setzeFehlerVars(fehler, Datum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            // document = null;
        }
    }


    private  static  void importItem(String filePath) {

        try {
            if (business.importItem(filePath)) {
                System.out.println("item added");
                business.showItems();

            } else {
                // benachrichtigeUserImFehlerfall(psDateiPfad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Finally, you should release the Document Object variables
            // item = null;
        }
    }
}
