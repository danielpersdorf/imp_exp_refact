package com.imp_exp.refact.basicModel;

import junitparams.JUnitParamsRunner;
import org.ini4j.Ini;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitParamsRunner.class)
class import_exportTest {

    @ParameterizedTest
    @CsvSource({
            "Import,Partner,Y",
            "Import,Item,Y",
            "Export,PartnerPath,externalData/partner/",
            "Export,Partner,N" })
    void test_readFromIni(String section, String key, String expected) throws IOException {

        Ini ini = new Ini(new File("src/main/java/com/imp_exp/refact/basicModel/imp_exp.ini"));

        assertEquals(expected, ini.get(section, key));
    }

}