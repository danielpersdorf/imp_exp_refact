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
        Import importer = new Import();
        importer.objNr = String.valueOf(2);
        importer.doImports();
    }

    @Test
    void importItem() {
        Import importer = new Import();
        importer.objNr = String.valueOf(4);
        importer.doImports();
    }

    @Test
    void importDocument() {
        Import importer = new Import();
        importer.objNr = String.valueOf(17);
        importer.doImports();
    }
}