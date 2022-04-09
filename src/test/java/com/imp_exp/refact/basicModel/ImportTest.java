package com.imp_exp.refact.basicModel;

import com.imp_exp.refact.tinyErpModel.BusinessService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ImportTest {

    static int partnersCountStart;
    static int itemsCountStart;
    static int documentsCountStart;

    @BeforeAll
    static void Before() throws IOException {
        import_export.business = new BusinessService();
    }

    /*
    @Test
    void importPartner() {
        // arrange
        String job = "src/main/java/com/imp_exp/refact/externalData/partners/partner.xml";
        Import importer = new Import();
        importer.objNr = String.valueOf(2);
        // act
        importer.doImport(job);
    }*/


    @Test
    void importPartner_shouldIncreaseTotalPartners() {
        // arrange
        partnersCountStart = import_export.business.partners.size();
        String job = "src/main/java/com/imp_exp/refact/externalData/partners/partner.xml";
        Import importer = new Import();
        importer.objNr = String.valueOf(2);

        // act
        importer.doImport(job);
        int partnersCount = import_export.business.partners.size();

        // assert
        assertEquals(partnersCountStart + 1, partnersCount);
    }

    @Test
    void importItem() {
        // arrange
        itemsCountStart = import_export.business.items.size();
        String job = "src/main/java/com/imp_exp/refact/externalData/items/item.xml";
        Import importer = new Import();
        importer.objNr = String.valueOf(4);

        // act
        importer.doImport(job);
        int itemsCount = import_export.business.items.size();

        assertEquals(itemsCountStart + 1, itemsCount);
    }

    @Test
    void importDocument() {
        // arrange
        documentsCountStart = import_export.business.documents.size();
        String job = "src/main/java/com/imp_exp/refact/externalData/documents/orders/order.xml";
        Import importer = new Import();
        importer.objNr = String.valueOf(17);

        importer.doImport(job);
        int documentsCount = import_export.business.documents.size();

        assertEquals(documentsCountStart + 1, documentsCount);
    }
}