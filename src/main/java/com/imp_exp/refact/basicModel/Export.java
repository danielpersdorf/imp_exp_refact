package com.imp_exp.refact.basicModel;

import com.imp_exp.refact.tinyErpModel.BusinessService;

import java.io.IOException;

/** In Java static classes are not allowed here
 * */
public class Export {

    private static BusinessService business = import_export.business;

    public static void exportPartner() {
        System.out.println("started export of partner");
        String location = "src/main/java/com/imp_exp/refact/externalData/partners/partner.xml";
        int id = 11;
        Boolean partnerExported = null;
        try {
            partnerExported = business.exportPartner(id, location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (partnerExported) {
            System.out.println("exported partner " + id  + " to " + location);
        }
    }

    public static void exportItem() {
        System.out.println("started export of item");
        String location = "src/main/java/com/imp_exp/refact/externalData/items/item.xml";
        int id = 4;
        Boolean itemExported = null;
        try {
            itemExported = business.exportItem(id, location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (itemExported) {
            System.out.println("exported item " + id  + " to " + location);
        }
    }

    public static void exportDocument() {
        System.out.println("started export of document");
        String location = "src/main/java/com/imp_exp/refact/externalData/documents/document.xml";
        int id = 1;
        Boolean documentExported = null;
        try {
            documentExported = business.exportDocument(id, location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (documentExported) {
            System.out.println("exported document " + id  + " to " + location);
        }
    }
}
