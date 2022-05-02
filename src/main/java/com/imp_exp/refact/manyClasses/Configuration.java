package com.imp_exp.refact.manyClasses;

import static java.lang.Integer.parseInt;

public class Configuration {

    public Boolean isItemChangeActive;
    public String itemChangeTypes;

    public Boolean isPartnerChangeActive;
    public String partnerChangeTypes;

    public int archiveDuration;


    public Configuration() {
        isItemChangeActive = import_export.ini.get("ImportSettings", "ChangeItem").equals("Y");
        itemChangeTypes = import_export.ini.get("ImportSettings", "ChangeItemObjectTypes");
        isPartnerChangeActive = import_export.ini.get("ImportSettings", "ChangePartner").equals("Y");
        partnerChangeTypes = import_export.ini.get("ImportSettings", "ChangePartnerObjectTypes");
        archiveDuration = parseInt(import_export.ini.get("Settings","ArchiveDuration"));
    }

}
