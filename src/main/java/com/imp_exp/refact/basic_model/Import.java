package com.imp_exp.refact.basic_model;

import com.imp_exp.refact.tinyErpModel.BusinessService;

public class Import {
    private static BusinessService business;

    public static void prepareImport() {  }

    public static void doImportsOn(BusinessService business) {
        System.out.println("started import");
        Boolean partnerAdded = business.addPartner("Tony Stark");
        if (partnerAdded) { System.out.println("partner added"); }
        business.showPartners();
    }
}
