package com.imp_exp.refact.manyClasses;

import com.imp_exp.refact.tinyErpModel.BusinessService;

import java.io.IOException;


public class Export {

    private static BusinessService business = import_export.business;

    public static void exportPartner(int id) {
        System.out.println("started export of partner");
        String location = "src/main/java/com/imp_exp/refact/externalData/partners/partner.xml";
        try {
            if (business.exportPartner(id, location)) {
                System.out.println("exported partner " + id  + " to " + location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportItem(int id) {
        System.out.println("started export of item");
        String location = "src/main/java/com/imp_exp/refact/externalData/items/item.xml";
        try {
            if (business.exportItem(id, location)) {
                System.out.println("exported item " + id  + " to " + location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportDocument(int id) {
        System.out.println("started export of document");
        String location = "src/main/java/com/imp_exp/refact/externalData/documents/document.xml";
        try {
            if (business.exportDocument(id, location)) {
                System.out.println("exported document " + id  + " to " + location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
