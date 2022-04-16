package com.imp_exp.refact.firstCleanup;

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
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

class ImportTest {

    static int partnersCountStart;
    static int itemsCountStart;
    static int documentsCountStart;

    @BeforeAll
    static void Before() throws IOException {
        import_export.business = new BusinessService();
        import_export.ini = new Ini(new File("src/main/java/com/imp_exp/refact/firstCleanup/imp_exp.ini"));
    }

    /** to ensure files to move are there if removed by other test */
    void help_exportPartner() throws IOException {
        Export export = new Export();
        export.exportPartner(11);
    }

    /** to ensure files to move are there if removed by other test */
    void help_exportItem() throws IOException {
        Export export = new Export();
        export.exportItem(4);
    }

    /** to ensure files to move are there if removed by other test */
    void help_exportOrder() throws IOException {
        // export a order called document
        Export export = new Export();
        export.exportDocument(1);

        // and move to order.xml
        String filePath = "src/main/java/com/imp_exp/refact/externalData/documents/document.xml";
        String targetPath = "src/main/java/com/imp_exp/refact/externalData/documents/orders/order.xml";

        Path fileToMove = Paths.get(filePath);
        Path target = Paths.get(targetPath);
        Files.move(fileToMove, target, REPLACE_EXISTING);
    }

    @Test
    void importPartner_shouldIncreaseTotalPartners() throws IOException {
        // arrange
        help_exportPartner();
        partnersCountStart = import_export.business.partners.size();
        String job = "src/main/java/com/imp_exp/refact/externalData/partners/partner.xml";

        Import importer = new Import();
        importer.objNr = String.valueOf(2);
        importer.objTyp = "partner";
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
        // arrange
        help_exportItem();
        itemsCountStart = import_export.business.items.size();
        String job = "src/main/java/com/imp_exp/refact/externalData/items/item.xml";

        Import importer = new Import();
        importer.objNr = String.valueOf(4);
        importer.objTyp = "item";
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
        // arrange
        help_exportOrder();
        documentsCountStart = import_export.business.documents.size();
        String job = "src/main/java/com/imp_exp/refact/externalData/documents/orders/order.xml";

        Import importer = new Import();
        importer.objNr = String.valueOf(17);
        importer.objTyp = "order";
        importer.iniSection = "Import";
        importer.iniImportArchivePath = "OrderArchivePath";
        importer.iniImportErrorPath = "OrderErrorPath";

        // act
        importer.doImport(job);
        int documentsCount = import_export.business.documents.size();

        assertEquals(documentsCountStart + 1, documentsCount);
    }

    @Test
    void test_moveFileToArchive() throws IOException {

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

        Path fileToMovePath = Paths.get(filePath);
        Path targetPath = Paths.get(archiveFileName);
        Files.move(fileToMovePath, targetPath);
    }

    @Test
    void test_deleteOldArchiveFiles_fromSingleDirectory() {
        Import.deleteOldArchiveFiles("src/main/java/com/imp_exp/refact/externalData/partners/archive/");
    }

    @Test
    void test_deleteOldArchiveFiles_fromMultipleDirectories() {
        String[] directories = new String[] {
                "src/main/java/com/imp_exp/refact/externalData/items/archive/",
                "src/main/java/com/imp_exp/refact/externalData/documents/orders/archive/"};
        Import.deleteOldArchiveFiles(directories);
    }
}