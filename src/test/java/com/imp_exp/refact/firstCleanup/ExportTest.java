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
    void exportPartner() {
        Export export = new Export();
        export.exportPartner();
    }

    @Test
    void exportItem() {
        Export export = new Export();
        export.exportItem();
    }

    @Test
    void exportDocument() {
        Export export = new Export();
        export.exportDocument();
    }
}