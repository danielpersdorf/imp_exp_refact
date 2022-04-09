package com.imp_exp.refact.basicModel;

import com.imp_exp.refact.tinyErpModel.BusinessService;

import java.io.IOException;


public class import_export {

    private static Boolean componentsInitialised;
    private static Boolean programStop;

    public static BusinessService business;

    public static String ObjNr;


    public static void main(String args[]) {
        System.out.println("Started imp_exp ");
        componentsInitialised = initComponents();
        if (componentsInitialised) {
            doWork();
        }
    }

    private static Boolean initComponents() {
        // init read resourceData (ini)
        // init logger
        // init programStop
        // init connection
        try {
            business = new BusinessService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("initialized components");
        return true;
    }

    private static void doWork() {

        System.out.println("Start working ");

        programStop = false;
        Boolean importIniKeys = true;
        Boolean fileInDir = true;
        Boolean foundJobsOnDB = false;

        do {
            // originally like this:
            // ----- imports ------------------------------------------------------------------------------------
            // for every object type in this list
            String[] tmp = { "2", "4", "13", "15", "17", "18", "19", "20", "22", "63" };
            for(String x : tmp) {
                ObjNr = x;
                Import.prepareImport();
                // wenn ImportTrigger = J -> importieren
                // if (IniFileHelper.ReadValue(Import.Section, Import.ImportTrigger, Import.FilePath, "") == "J")

                if (importIniKeys) {
                    // foreach (string job in Directory.GetFiles(IniFileHelper.ReadValue(Import.Section, Import.ImportVerzeichnis, Import.FilePath, ""), "*.xml"))
                    if(fileInDir) {
                        // System.out.println(job);
                        // logger.Info("Job: " + job);
                        // Import.importDatei(job);
                        Import.doImports();
                    }
                }
            }


            // ----- Exports ---------------------------------------------------------------------------------
            // get jobs from DB via BusinessService
            // List<Tuple<string, string>> Jobs = DI_API_request.GetJobs(DI_API.connection);
            // foreach (var job in Jobs)
            if (foundJobsOnDB) {
                switch ("job.Item1")
                {
                    // Table OCRD - Customers (GP) / Primary Key: CardCode / Object Type = 2
                    case "2":
                        // ObjNr = "2";
                        //Export.exportGP(job.Item2);
                        // Export.doExport();
                        break;
                    /*
                    //Table: OITM - Items , Primary Key: ItemCode, Object Type: 4
                    case "4":
                        ObjNr = "4";
                        Export.exportArtikel(job.Item2);
                        break;

                    //Table: OINV - A/R Invoice, Primary Key: DocEntry, Object Type: 13
                    case "13":
                        ObjNr = "13";
                        Export.export(Int32.Parse(job.Item2));
                        break;

                    //Table: ODLN - 	Delivery Note , Primary Key: DocEntry, Object Type: 15
                    case "15":
                        ObjNr = "15";
                        Export.export(Int32.Parse(job.Item2));
                        break;

                    // Table: ORDR - Order, Primary Key: DocEntry, Object Type: 17
                    case "17":
                        ObjNr = "17";
                        Export.export(Int32.Parse(job.Item2));
                        break;

                    //Table: OPRJ - 		Prime key: ProjectCode, Object Type: 63
                    case "63":
                        ObjNr = "63";
                        Export.exportProjekte((job.Item2));
                        break;
                        */
                    default:
                }
            }

            // doUpdate();
            // doFillUdt();
            programStop = getProgramStop();
        } while (programStop == false);
    }

    /** to import a partner */
    // private static void doImport() {
    // System.out.println("started import");
    // Boolean partnerAdded = business.addPartner("Tony Stark");
    // if (partnerAdded) { System.out.println("partner added"); }
    // business.showPartners();
    // }

    /** to export a partner to xml  */
    /*private static void doExport() {
        System.out.println("started export");
        String location = "src/main/resources/partner.xml";

        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File(location), new Partner("XML Partner"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(location);
        System.out.println("exported partner to " + location);
    }*/

    // private static void doUpdate() { System.out.println("started update"); }
    // private static void doFillUdt() { System.out.println("started fillUdt"); }
    private static Boolean getProgramStop() { return true; }
}
