package com.imp_exp.refact.basic_model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imp_exp.refact.tinyErpModel.BusinessService;
import com.imp_exp.refact.tinyErpModel.Partner;

import java.io.File;
import java.io.IOException;


public class Import {

    private static Partner partner;
    private static BusinessService business = import_export.business;
    private static ObjectMapper objectMapper = new ObjectMapper();


    /**
     * to decide which parts of the ini to read
     */
    public static void prepareImport() {
        /*switch (Program.ObjNr)
        {
            case "2":
                ObjNr = Program.ObjNr;
                ObjBez = "Geschäftspartner";
                ImportTrigger = "ImportGeschaeftspartner";
                ImportVerzeichnis = "ImportGeschaeftspartnerVerzeichnis";
                ImportVerzeichnisArchiv = "ImportGeschaeftspartnerArchiv";
                ImportVerzeichnisFehler = "ImportGeschaeftspartnerFehler";
                sIPF_ObTy = Convert.ToString(2);
                sIPF_TaNa = "OCRD";
                break;

            case "4":
                ObjNr = Program.ObjNr;
                ObjBez = "Artikel";
                ImportTrigger = "ImportArtikel";
                ImportVerzeichnis = "ImportArtikelVerzeichnis";
                ImportVerzeichnisArchiv = "ImportArtikelArchiv";
                ImportVerzeichnisFehler = "ImportArtikelFehler";
                sIPF_ObTy = Convert.ToString(4);
                sIPF_TaNa = "OITM";
                // oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oItems);
                break;

            case "13":
                ObjNr = Program.ObjNr;
                ObjBez = "Ausgangsrechnung";
                ImportTrigger = "ImportAusgangsrechnung";
                ImportVerzeichnis = "ImportAusgangsrechnungVerzeichnis";
                ImportVerzeichnisArchiv = "ImportAusgangsrechnungVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportAusgangsrechnungVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(13);
                sIPF_TaNa = "OINV";
                oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oInvoices);
                break;

            case "15":
                ObjNr = Program.ObjNr;
                ObjBez = "Lieferung";
                ImportTrigger = "ImportLieferung";
                ImportVerzeichnis = "ImportLieferungVerzeichnis";
                ImportVerzeichnisArchiv = "ImportLieferungVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportLieferungVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(15);
                sIPF_TaNa = "ODLN";
                oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oDeliveryNotes);
                break;

            case "17":
                ObjNr = Program.ObjNr;
                ObjBez = "Kundenauftrag";
                ImportTrigger = "ImportKundenauftrag";
                ImportVerzeichnis = "ImportKundenauftragVerzeichnis";
                ImportVerzeichnisArchiv = "ImportKundenauftragVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportKundenauftragVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(17);
                sIPF_TaNa = "ORDR";
                // oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oOrders);
                // nicht mehr aus der static DI_API.connection sondern oCompany Objekt benutzen
                oDoc_Type = Program.oCompany.GetBusinessObject(BoObjectTypes.oOrders);
                break;

            case "18":
                ObjNr = Program.ObjNr;
                ObjBez = "Eingangsrechnung";
                ImportTrigger = "ImportEingangsrechnung";
                ImportVerzeichnis = "ImportEingangsrechnungVerzeichnis";
                ImportVerzeichnisArchiv = "ImportEingangsrechnungVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportEingangsrechnungVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(18);
                sIPF_TaNa = "OPCH";
                oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oPurchaseInvoices);
                break;

            case "19":
                ObjNr = Program.ObjNr;
                ObjBez = "EingangsGutschrift";
                ImportTrigger = "ImportEingangsGutschrift";
                ImportVerzeichnis = "ImportEingangsGutschriftVerzeichnis";
                ImportVerzeichnisArchiv = "ImportEingangsGutschriftVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportEingangsGutschriftVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(19);
                sIPF_TaNa = "ORPC";
                oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oPurchaseCreditNotes);
                break;

            case "20":
                ObjNr = Program.ObjNr;
                ObjBez = "Wareneingang";
                ImportTrigger = "ImportWareneingang";
                ImportVerzeichnis = "ImportWareneingangVerzeichnis";
                ImportVerzeichnisArchiv = "ImportWareneingangVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportWareneingangVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(20);
                sIPF_TaNa = "OPDN";
                oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oPurchaseDeliveryNotes);
                break;

            case "22":
                ObjNr = Program.ObjNr;
                ObjBez = "Bestellung";
                ImportTrigger = "ImportBestellung";
                ImportVerzeichnis = "ImportBestellungVerzeichnis";
                ImportVerzeichnisArchiv = "ImportBestellungVerzeichnisArchiv";
                ImportVerzeichnisFehler = "ImportBestellungVerzeichnisFehler";
                sIPF_ObTy = Convert.ToString(22);
                sIPF_TaNa = "OPOR";
                oDoc_Type = DI_API.connection.company.GetBusinessObject(BoObjectTypes.oPurchaseOrders);
                break;
        }*/
    }

    public static void doImports()  {

        System.out.println("started import");

        String objTyp = "partner";

        switch (objTyp) {
            case "partner":
                // if(read config file trigger for imports) { }
                importPartner("src/main/resources/partner.json");
                break;
            case "document":
                importDocument("src/main/resources/document.json");
                break;
            case "item":
                importItem("src/main/resources/item.json");
                break;
            default:
                System.out.println("No Valid Business Object found");
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
        partner = new Partner();

        try {
            partner = objectMapper.readValue(new File(filePath), Partner.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            if (business.addPartner(partner)) {
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
            partner = null;
        }
    }

    private  static  void importDocument(String filePath) { }
    private  static  void importItem(String filePath) { }
}
