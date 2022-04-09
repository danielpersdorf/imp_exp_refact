package com.imp_exp.refact.tinyErpModel;

import java.io.IOException;
import java.util.List;


public class BusinessService {

    public List<Partner> partners;
    public List<Item> items;
    public List<Document> documents;

    DocumentWrapper wrapper;
    JsonSerializer serializer = new JsonSerializer();
    XmlSerializer xmlSerializer = new XmlSerializer();

    private Boolean partnerAdded;
    private Boolean allPartnersSerialized;

    private Boolean itemAdded;
    private Boolean allItemsSerialized;

    private Boolean documentAdded;
    private Boolean allDocumentsSerialized;


    public BusinessService() throws IOException {

        System.out.println("Started BusinessService");

        partners = serializer.deserializePartners();
        showPartners();

        items = serializer.deserializeItems();
        showItems();

        wrapper = serializer.deserializeDocuments();
        documents = wrapper.documents;
        showDocuments();
    }


    public void showPartners() {
        for (Partner partner : partners) { System.out.println(partner.id + " " + partner.name); }
    }

    public void showItems() {
        for (Item item : items) { System.out.println(item.toString());}
    }

    public void showDocuments() {
        for (Document document : documents) { System.out.println(document.toString()); }
    }


    public Boolean addPartner(Partner partner) throws IOException {
        int nextCode = partners.size() + 1;
        Partner updatedPartner = new Partner(nextCode , partner.name);
        partnerAdded = partners.add(updatedPartner);
        allPartnersSerialized = serializer.serializeAllPartners(partners);
        return partnerAdded & allPartnersSerialized;
    }
    public Boolean addItem(Item item) throws IOException {
        item.id = documents.size() + 1;
        itemAdded = items.add(item);
        allItemsSerialized = serializer.serializeAllItems(items);
        return itemAdded & allItemsSerialized;
    }
    public Boolean addDocument(Document document) throws IOException {
        document.id = documents.size() + 1;
        documentAdded = documents.add(document);
        wrapper.documents = documents;
        allDocumentsSerialized = serializer.serializeAllDocuments(wrapper);
        return documentAdded & allDocumentsSerialized;
    }

    public Boolean exportPartner(int id, String location) throws IOException {
        Partner partner = partners.get(id - 1);
        xmlSerializer.serializePartner(partner, location);
        return true;
    }
    public Boolean exportItem(int id, String location) throws IOException {
        Item item = items.get(id);
        xmlSerializer.serializeItem(item, location);
        return true;
    }
    public Boolean exportDocument(int id, String location) throws IOException {
        Document document = documents.get(id);
        xmlSerializer.serializeDocument(document, location);
        return true;
    }

    public Boolean importPartner(String filePath) throws IOException {
        Partner partner = xmlSerializer.deserializePartner(filePath);
        return addPartner(partner);
    }
    public Boolean importItem(String filePath) throws IOException {
        Item item = xmlSerializer.deserializeItem(filePath);
        return addItem(item);
    }
    public Boolean importDocument(String filePath) throws IOException {
        Document document = xmlSerializer.deserializeDocument(filePath);
        return addDocument(document);
    }
}


