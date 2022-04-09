package com.imp_exp.refact.basicModel;

import com.imp_exp.refact.tinyErpModel.BusinessService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class ImportTest {

    @BeforeAll
    static void Before() throws IOException {
        import_export.business = new BusinessService();
    }

    @Test
    void importPartner() {
        String job = "src/main/java/com/imp_exp/refact/externalData/partners/partner.xml";
        Import importer = new Import();
        importer.objNr = String.valueOf(2);
        importer.doImport(job);
    }

    @Test
    void importItem() {
        String job = "src/main/java/com/imp_exp/refact/externalData/items/item.xml";
        Import importer = new Import();
        importer.objNr = String.valueOf(4);
        importer.doImport(job);
    }

    @Test
    void importDocument() {
        String job = "src/main/java/com/imp_exp/refact/externalData/documents/document.xml";
        Import importer = new Import();
        importer.objNr = String.valueOf(17);
        importer.doImport(job);
    }
}