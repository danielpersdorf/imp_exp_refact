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
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

class ImportTest {

    static int partnersCountStart;
    static int itemsCountStart;
    static int documentsCountStart;

    @BeforeAll
    static void Before() throws IOException {
        import_export.business = new BusinessService();
        import_export.ini = new Ini(new File("src/main/java/com/imp_exp/refact/basicModel/imp_exp.ini"));
    }

    /** to ensure files to move are there if removed by other test */
    void help_exportPartner() throws IOException {
        Export export = new Export();
        export.exportPartner();
    }

    /** to ensure files to move are there if removed by other test */
    void help_exportItem() throws IOException {
        Export export = new Export();
        export.exportItem();
    }

    /** to ensure files to move are there if removed by other test */
    void help_exportOrder() throws IOException {
        // export a order called document
        Export export = new Export();
        export.exportDocument();

        // and move to order.xml
        String filePath = "src/main/java/com/imp_exp/refact/externalData/documents/document.xml";
        String targetPath = "src/main/java/com/imp_exp/refact/externalData/documents/orders/order.xml";

        Path fileToMove = Paths.get(filePath);
        Path target = Paths.get(targetPath);
        Files.move(fileToMove, target, REPLACE_EXISTING);
    }

    @Test
    void importPartner_shouldIncreaseTotalPartners() throws IOException {
        help_exportPartner();

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
    void importItem() throws IOException {

        help_exportItem();

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
    void importDocument() throws IOException {

        help_exportOrder();

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

        help_exportPartner();

        String filePath = "src/main/java/com/imp_exp/refact/externalData/partners/partner.xml";
        String archive = "src/main/java/com/imp_exp/refact/externalData/partners/archive/";;
        String date = "_" + LocalDateTime.now();
        date = date.replace(":", "-");

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