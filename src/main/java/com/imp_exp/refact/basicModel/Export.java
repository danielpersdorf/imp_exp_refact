package com.imp_exp.refact.basicModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.imp_exp.refact.tinyErpModel.BusinessService;
import com.imp_exp.refact.tinyErpModel.Item;
import com.imp_exp.refact.tinyErpModel.Partner;

import java.io.File;
import java.io.IOException;

/** In Java static classes are not allowed here
 * */
public class Export {

    private static BusinessService business = import_export.business;
    private static XmlMapper xmlMapper = new XmlMapper();

    /** to export a partner to xml  */
    public static void exportPartner() {
        System.out.println("started export of partner");
        String location = "src/main/java/com/imp_exp/refact/dataLayer/partner.xml";

        try {
            xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            xmlMapper.writeValue(new File(location), new Partner("XML Partner"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // File file = new File(location);
        System.out.println("exported partner to " + location);
    }

    /** to export an item to xml  */
    public static void exportItem() {
        System.out.println("started export of item");
        String location = "src/main/java/com/imp_exp/refact/dataLayer/item.xml";

        try {
            xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            xmlMapper.writeValue(new File(location), new Item("XML Item"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(location);
        System.out.println("exported item to " + location);
    }

    /** to export an document to xml  */
    public static void exportDocument() {
        System.out.println("started export of document");
        String location = "src/main/java/com/imp_exp/refact/dataLayer/document.xml";

        try {
            xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            xmlMapper.writeValue(new File(location), new Item("XML Document"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(location);
        System.out.println("exported document to " + location);
    }

}
