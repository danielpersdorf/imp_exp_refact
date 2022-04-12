package com.imp_exp.refact.tinyErpModel;

import java.io.IOException;
import java.util.List;


public class BusinessService {

    public static List<Partner> partners;
    public static List<Item> items;
    public static List<Document> documents;

    public static Partner oPartnerCompany;
    public static Item oItemCompany;
    public static Document oDocumentCompany;

    static DocumentWrapper wrapper;
    static JsonSerializer serializer = new JsonSerializer();
    XmlSerializer xmlSerializer = new XmlSerializer();

    private static Boolean partnerAdded;
    private static Boolean allPartnersSerialized;

    private static Boolean itemAdded;
    private static Boolean allItemsSerialized;

    private static Boolean documentAdded;
    private static Boolean allDocumentsSerialized;

    private static int nextCode;


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

    public static Boolean addPartner(Partner partner) throws IOException {
        nextCode = partners.size() + 1;
        Partner updatedPartner = new Partner(nextCode , partner.name);
        partnerAdded = partners.add(updatedPartner);
        allPartnersSerialized = serializer.serializeAllPartners(partners);
        return partnerAdded & allPartnersSerialized;
    }
    public static Boolean addItem(Item item) throws IOException {
        nextCode = items.size();
        Item updatedItem = new Item(nextCode, item.name);
        itemAdded = items.add(updatedItem);
        allItemsSerialized = serializer.serializeAllItems(items);
        return itemAdded & allItemsSerialized;
    }
    public static Boolean addDocument(Document document) throws IOException {
        nextCode = documents.size() + 1;
        document.setID(nextCode);
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

    public Partner getPartnerFromXML(String filePath) {
        try {
            oPartnerCompany = xmlSerializer.deserializePartner(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oPartnerCompany;
    }
    public Item getItemFromXML(String filePath) {
        try {
            oItemCompany = xmlSerializer.deserializeItem(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oItemCompany;
    }
    public Document getDocumentFromXML(String filePath) {
        // must be memorized in local variable too
        try {
            oDocumentCompany = xmlSerializer.deserializeDocument(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oDocumentCompany;
    }
}


