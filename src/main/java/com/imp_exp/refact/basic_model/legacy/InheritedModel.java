package com.imp_exp.refact.basic_model.legacy;

import java.util.*;


public class InheritedModel {

    private static Boolean componentsInitialised;
    private static Boolean programStop;
    private static BusinessService business;

    public static void main(String args[]) {
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
        business = new BusinessService();
        return true;
    }

    private static void doWork() {
        programStop = false;
        Boolean ImportIniKeys = true;
        Boolean FileInDir = true;
        do {
            if (ImportIniKeys) {
                if (FileInDir) {
                    Import.doImportsOn(business);
                }
            }
            doExport();
            doUpdate();
            doFillUdt();
            programStop = getProgramStop();
        } while (programStop == false);
    }

    // private static void doImport() {
        // System.out.println("started import");
        // Boolean partnerAdded = business.addPartner("Tony Stark");
        // if (partnerAdded) { System.out.println("partner added"); }
        // business.showPartners();
    // }

    private static void doExport() { System.out.println("started export"); }
    private static void doUpdate() { System.out.println("started update"); }
    private static void doFillUdt() { System.out.println("started fillUdt"); }
    private static Boolean getProgramStop() { return true; }
}

class Import {
    private static BusinessService business;
    public static void prepareImport() {  }
    public static void doImportsOn(BusinessService business) {
        System.out.println("started import");
        Boolean partnerAdded = business.addPartner("Tony Stark");
        if (partnerAdded) { System.out.println("partner added"); }
        business.showPartners();
    }
}

class BusinessService {
    public List<Partner> partners;
    public List<Document> documents;
    public List<Item> items;
    public BusinessService() {
        partners = new ArrayList<Partner>();
        partners.add(new Partner("Mr.Spock"));
    }

    public void showPartners() {
        for (Partner partner : partners) { System.out.println(partner.name); }
    }
    public Boolean addPartner(String name) { return partners.add(new Partner(name)); }
}

class Partner {
    public int id;
    public String name;
    public Partner(String name) { this.name = name; }
}

class Document {}
class Item {}