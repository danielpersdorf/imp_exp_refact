package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class XmlSerializer {

    XmlMapper xmlMapper = new XmlMapper();

    // export Partner imp_exp
    public Boolean serializePartner(Partner partner, String location) throws IOException {
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.writeValue(new File(location), partner);
        return true;
    }

    // import Partner imp_exp
    public Partner deserializePartner(String filePath) throws IOException {
        return xmlMapper.readValue(new File(filePath), Partner.class);
    }

    public Boolean serializeItem(Item item, String location) throws IOException {
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.writeValue(new File(location), item);
        return true;
    }

    public Item deserializeItem(String filePath) throws IOException {
        return xmlMapper.readValue(new File(filePath), Item.class);
    }

    public Boolean serializeDocument(Document document, String location) throws IOException {
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.writeValue(new File(location), document);
        return true;
    }

    public Document deserializeDocument(String filePath) throws IOException {
        // xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper.readValue(new File(filePath), Document.class);
    }
}
