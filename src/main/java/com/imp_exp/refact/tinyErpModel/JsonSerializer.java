package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class JsonSerializer {

    ObjectMapper objectMapper = new ObjectMapper();

    public Boolean serializeAllPartners(List<Partner> partners) throws IOException {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allPartner.json"), partners);
        return true;
    }

    public List<Partner> deserializePartners() throws IOException {
        return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allPartner.json"), new TypeReference<List<Partner>>(){});
    }

    public Boolean serializeAllItems(List<Item> items) throws IOException {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allItems.json"), items);
        return true;
    }

    public List<Item> deserializeItems() throws IOException {
        return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allItems.json"), new TypeReference<List<Item>>(){});
    }

    public Boolean serializeAllDocuments(DocumentWrapper wrapper) throws IOException {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments.json"), wrapper);
        return true;
    }

    public DocumentWrapper deserializeDocuments() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments.json"), DocumentWrapper.class);
    }
}
