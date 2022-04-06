package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.imp_exp.refact.smallXmpls.BaseClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class BusinessService {


    public List<Partner> partners;
    public List<Item> items;
    public List<Document> documents;

    ObjectMapper objectMapper = new ObjectMapper();

    // extra marshaller
    DocumentMarshaller marshaller = new DocumentMarshaller();

    private Boolean partnerAdded;
    private Boolean allPartnersSerialized;

    private Boolean itemAdded;
    private Boolean allItemsSerialized;

    private Boolean documentAdded;
    private Boolean allDocumentsSerialized;

    public BusinessService() throws IOException {

        System.out.println("Started BusinessService");

        partners = deserializePartners();
        showPartners();

        items = deserializeItems();
        showItems();

        marshaller = deserializeDocuments();
        documents = marshaller.documents;
        showDocuments();
    }

    // partners
    public void showPartners() {
        for (Partner partner : partners) { System.out.println(partner.id + " " + partner.name); }
    }

    public Boolean addPartner(Partner partner) throws IOException {
        int nextCode = partners.size() + 1;
        Partner updatedPartner = new Partner(nextCode , partner.name);
        partnerAdded = partners.add(updatedPartner);
        allPartnersSerialized = serializeAllPartners(partners);
        return partnerAdded & allPartnersSerialized;
    }

    public Boolean serializeAllPartners(List<Partner> partners) throws IOException {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allPartner.json"), partners);
        return true;
    }

    public List<Partner> deserializePartners() throws IOException {
        return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allPartner.json"), new TypeReference<List<Partner>>(){});
    }


    // items
    public void showItems() {
        for (Item item : items) { System.out.println(item.toString());}
    }

    public Boolean addItem(Item item) throws IOException {
        //document.setID(documents.size() + 1);
        itemAdded = items.add(item);
        allItemsSerialized = serializeAllItems(items);
        return itemAdded & allItemsSerialized;
    }

    public Boolean serializeAllItems(List<Item> items) throws IOException {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allItems.json"), items);
        return true;
    }

    public List<Item> deserializeItems() throws IOException {
        return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allItems.json"), new TypeReference<List<Item>>(){});
    }


    // documents
    public void showDocuments() {
        for (Document document : documents) { System.out.println(document.toString()); }
    }

    public Boolean addDocument(Document document) throws IOException {
        // int nextCode = documents.size() + 1;
        documentAdded = documents.add(document);
        marshaller.documents = documents;
        allDocumentsSerialized = serializeAllDocuments(marshaller);
        return documentAdded & allDocumentsSerialized;
    }

    public Boolean serializeAllDocuments(DocumentMarshaller marshaller) throws IOException {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments.json"), marshaller);
        return true;
    }

    public DocumentMarshaller deserializeDocuments() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments.json"), new TypeReference<ArrayList<Document>>(){});
        return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments.json"), DocumentMarshaller.class);
    }

}


class DocumentMarshaller {
    public List<Document> documents;
    public DocumentMarshaller() {
        this.documents = new ArrayList<Document>();
    }
    public DocumentMarshaller(List list) {
        this.documents = list;
    }
}
