package com.imp_exp.refact.basic_model;

import com.imp_exp.refact.tinyErpModel.BusinessService;

import java.io.IOException;

public class Import {

    private static Boolean partnerAdded = false;

    public static void prepareImport() {  }

    public static void doImportsOn(BusinessService business)  {

        System.out.println("started import");

        String objTyp = "partner";

        switch (objTyp) {
            case "partner":
                importPartnerTo(business);
                break;
            case "document":
                importDocumentTo(business);
                break;
            case "item":
                importItemTo(business);
                break;
            default:
                System.out.println("No Valid Business Object found");
                break;
        }
    }

    private static void importPartnerTo(BusinessService business) {
        try {
            partnerAdded = business.addPartner("SerialTestPartner");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (partnerAdded) {
            System.out.println("partner added");
            business.showPartners();
        }
    }

    private  static  void importDocumentTo(BusinessService business) { }
    private  static  void importItemTo(BusinessService business) { }
}
