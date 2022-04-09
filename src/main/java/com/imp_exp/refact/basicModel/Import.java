package com.imp_exp.refact.basicModel;

import com.imp_exp.refact.tinyErpModel.*;

import java.io.IOException;

/** In Java static classes are not allowed here
 * */
public class Import {

    public static String objTyp;
    public static String objNr;

    private static BusinessService business = import_export.business;

    public static String iniSection;
    public static String iniTrigger;
    public static String iniImportPath;

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
                /*ImportVerzeichnisArchiv = "ImportGeschaeftspartnerArchiv";
                ImportVerzeichnisFehler = "ImportGeschaeftspartnerFehler";
                sIPF_ObTy = Convert.ToString(2);
                sIPF_TaNa = "OCRD";
                */
                break;

            case "4":
                objNr = import_export.ObjNr;
                objTyp = "item";
                iniSection = "Import";
                iniTrigger = "Item";
                iniImportPath = "ItemPath";
                /* ImportVerzeichnisArchiv = "ImportArtikelArchiv";
                ImportVerzeichnisFehler = "ImportArtikelFehler";
                sIPF_ObTy = Convert.ToString(4);
                sIPF_TaNa = "OITM"; */
                // oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oItems);*/
                break;
            case "17":
                objNr = import_export.ObjNr;
                objTyp = "Order";
                iniSection = "Import";
                iniTrigger = "Order";
                iniImportPath = "OrderPath";
                // ImportVerzeichnisArchiv = "ImportKundenauftragVerzeichnisArchiv";
                // ImportVerzeichnisFehler = "ImportKundenauftragVerzeichnisFehler";
                // sIPF_ObTy = Convert.ToString(17);
                // sIPF_TaNa = "ORDR";
                //// oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oOrders);
                //// nicht mehr aus der static DI_API.connection sondern oCompany Objekt benutzen
                // oDoc_Type = Program.oCompany.GetBusinessObject(BoObjectTypes.oOrders);
                break;

            case "13":
                objNr = import_export.ObjNr;
                objTyp = "Invoice";
                iniSection = "Import";
                iniTrigger = "Invoice";
                iniImportPath = "InvoicePath";
                /*
                ImportVerzeichnisArchiv = "ImportAusgangsrechnungVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportAusgangsrechnungVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(13);
                sIPF_TaNa = "OINV";
                oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oInvoices);
                */
                break;

            case "15":
                objNr = import_export.ObjNr;
                objTyp = "Delivery";
                iniSection = "Import";
                iniTrigger = "Delivery";
                iniImportPath = "DeliveryPath";
                /*
                ImportVerzeichnisArchiv = "ImportLieferungVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportLieferungVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(15);
                sIPF_TaNa = "ODLN";
                oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oDeliveryNotes);
                */
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
        // string archiv = IniFileHelper.ReadValue(Section, ImportVerzeichnisArchiv, FilePath, "");
        // string fehler = IniFileHelper.ReadValue(Section, ImportVerzeichnisFehler, FilePath, "");
        // string Datum = "_" + DateTime.Now.ToString("yyyy-MM-dd-HH-mm-ss-fff");

        // wenn es ein Geschäftspartner ist, der importiert werden soll, so muss ein anderes Objekt erzeugt werden.
        // BusinessPartners oDocLocal = DI_API_request.connection.company.GetBusinessObjectFromXML(psDateiPfad, 0);

        try {

            if (business.importPartner(filePath)) {
                System.out.println("partner added");
                business.showPartners();

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
