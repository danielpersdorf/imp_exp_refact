package com.imp_exp.refact.firstCleanup;

import com.imp_exp.refact.tinyErpModel.BusinessService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class ExportTest {

    @BeforeAll
    static void Before() throws IOException {
        import_export.business = new BusinessService();
    }

    @Test
    void exportPartner_byID() {
        int id = 11;
        Export.exportPartner(id);
    }

    @Test
    void exportItem_byID() {
        int id = 4;
        Export.exportItem(id);
    }

    @Test
    void exportDocument_byID() {
        int id = 1;
        Export.exportDocument(id);
    }
}