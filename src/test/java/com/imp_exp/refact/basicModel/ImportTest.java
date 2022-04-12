package com.imp_exp.refact.basicModel;

import com.imp_exp.refact.tinyErpModel.BusinessService;
import org.ini4j.Ini;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ImportTest {

    static int partnersCountStart;
    static int itemsCountStart;
    static int documentsCountStart;

    @BeforeAll
    static void Before() throws IOException {
        import_export.business = new BusinessService();
        import_export.ini = new Ini(new File("src/main/java/com/imp_exp/refact/basicModel/imp_exp.ini"));
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
        importer.iniSection = "Import";
        importer.iniImportArchivePath = "PartnerArchivePath";
        importer.iniImportErrorPath = "PartnerErrorPath";

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
        importer.iniSection = "Import";
        importer.iniImportArchivePath = "ItemArchivePath";
        importer.iniImportErrorPath = "ItemErrorPath";

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
        importer.iniSection = "Import";
        importer.iniImportArchivePath = "OrderArchivePath";
        importer.iniImportErrorPath = "OrderErrorPath";

        importer.doImport(job);
        int documentsCount = import_export.business.documents.size();

        assertEquals(documentsCountStart + 1, documentsCount);
    }

    @Test
    void moveFileToArchive() throws IOException {
        String filePath = "src/main/java/com/imp_exp/refact/externalData/partners/partner.xml";
        String archive = "src/main/java/com/imp_exp/refact/externalData/partners/archive/";;
        String date = "_" + LocalDateTime.now();
        // date = date.replace(":", "_");

        int positionOfExtension = filePath.lastIndexOf(".");
        int positionOfSeperator = filePath.lastIndexOf("/");
        String fileNameExtension = filePath.substring(positionOfExtension);
        String fileName = filePath.substring(positionOfSeperator + 1, positionOfExtension );
        String archiveFileName = archive + fileName + date + fileNameExtension;

        // archiviereXmlDatei(psDateiPfad, archiv, Datum, oDocLocal.CardCode);
        Path fileToMovePath = Paths.get(filePath);
        Path targetPath = Paths.get(archiveFileName);
        Files.move(fileToMovePath, targetPath);
    }
}