package com.imp_exp.refact.basicModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.imp_exp.refact.tinyErpModel.BusinessService;
import com.imp_exp.refact.tinyErpModel.Partner;

import java.io.File;
import java.io.IOException;

/** In Java static classes are not allowed here
 * */
public class Export {

    private static Partner partner;
    private static BusinessService business = import_export.business;
    private static ObjectMapper objectMapper = new ObjectMapper();

    /** to export a partner to xml  */
    public static void doExport() {
        System.out.println("started export");
        String location = "src/main/resources/partner.xml";

        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File(location), new Partner("XML Partner"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(location);
        System.out.println("exported partner to " + location);
    }

}
