package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BusinessService {

    private List<Partner> partners;
    private List<Item> items;
    public static List<Document> documents;

    ObjectMapper objectMapper = new ObjectMapper();

    private Boolean partnerAdded;
    private Boolean allPartnersSerialized;

    private Boolean documentAdded;
    private Boolean allDocumentsSerialized;

    public BusinessService() throws IOException {

        System.out.println("Started BusinessService");

        partners = deserializePartners();
        // items = deserializeItems();
        documents = deserializeDocuments();

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
        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allPartner.json"), partners);
        return true;
    }

    public List<Partner> deserializePartners() throws IOException {
        return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allPartner.json"), new TypeReference<List<Partner>>(){});
    }

    // documents
    public void showDocuments() {
        for (Document document : documents) { System.out.println(document.toString());}
    }

    public Boolean addDocument(Document document) throws IOException {
        documentAdded = documents.add(document);
        allDocumentsSerialized = serializeAllDocuments(documents);
        return documentAdded & allDocumentsSerialized;
    }

    public Boolean serializeAllDocuments(List<Document> documents) throws IOException {
        // objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS , false);
        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments.json"), documents);
        return true;
    }

    public List<Document> deserializeDocuments() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments.json"), new TypeReference<List<Document>>(){});
    }

}
