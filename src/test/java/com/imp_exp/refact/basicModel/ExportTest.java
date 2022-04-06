package com.imp_exp.refact.basicModel;

import org.junit.jupiter.api.Test;


class ExportTest {

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