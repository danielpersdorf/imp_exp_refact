package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class JsonSerializerTest {

    JsonSerializer serializer = new JsonSerializer();

    @Test
    void test_serializeAllPartners() throws IOException {
        BusinessService business = new BusinessService();
        serializer.serializeAllPartners(business.partners);
    }

    @Test
    void test_deserializePartners() throws IOException {
        BusinessService business = new BusinessService();
        business.partners = serializer.deserializePartners();
        business.showPartners();
    }


    @Test
    void test_serializeAllItems() throws IOException {
        BusinessService business = new BusinessService();
        serializer.serializeAllItems(business.items);
    }

    @Test
    void test_deserializeItems() throws IOException {
        BusinessService business = new BusinessService();
        business.items = serializer.deserializeItems();
        business.showItems();
    }

//    @Test
//    void test_createFirstItemEntriesInDB() throws IOException {
//
//        BusinessService business = new BusinessService();
//
//        // create list of items
//        List<Item> newItems = new ArrayList<Item>();
//        // create items
//        Item item0 = new Item(0,"Test Item 0");
//        Item item1 = new Item(1,"Test Item 1");
//        Item item2 = new Item(2,"Test Item 2");
//        Item item3 = new Item(3,"Test Item 3");
//        Item item4 = new Item(4,"Test Item 4");
//        // populate list with items
//        newItems.add(item0);
//        newItems.add(item1);
//        newItems.add(item2);
//        newItems.add(item3);
//        newItems.add(item4);
//        // create the list of items
//        business.items = new ArrayList<Item>();
//        business.items = newItems;
//
//        // serialize data
//        business.serializer.serializeAllItems(business.items);
//
//        business.showItems();
//    }

    /*@Test
    void test_deserializeDocuments() throws IOException {
        BusinessService business = new BusinessService();
        business.documents = business.deserializeDocuments();
        business.showDocuments();

        // works with this string
        // [{"@class":"com.imp_exp.refact.tinyErpModel.Order","name":"Order","objType":"17","partner":{"id":0,"name":"Customer 0"},"items":[{"@class":"com.imp_exp.refact.tinyErpModel.Item","id":0,"name":"Test Item 0"},{"@class":"com.imp_exp.refact.tinyErpModel.Item","id":1,"name":"Test Item 1"},{"@class":"com.imp_exp.refact.tinyErpModel.Item","id":2,"name":"Test Item 2"},{"@class":"com.imp_exp.refact.tinyErpModel.Item","id":3,"name":"Test Item 3"},{"@class":"com.imp_exp.refact.tinyErpModel.Item","id":4,"name":"Test Item 4"}],"id":0}]
        // or this one without items
        //        [ {
        //            "@class":"com.imp_exp.refact.tinyErpModel.Order",
        //                    "id" : 2,
        //                    "name" : "Order",
        //                    "objType" : "17",
        //                    "partner" : {
        //                "id" : 0,
        //                        "name" : "Customer 0"
        //            }
        //        } ]
    }*/

    // test with wrapper
    @Test
    void test_serializeAList_withWrapper() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        //ClientClass clientClass = new ClientClass();
        DocumentWrapper wrapper = new DocumentWrapper();

        // create new partner
        Partner partner = new Partner("Customer 0");

        Document doc1 = new Order(1, partner);
        //Document doc2 = new Delivery(2, "Delivery");

        wrapper.documents.add(doc1);
        //clientClass.list.add(class2);

        objectMapper.writeValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments_test.json"), wrapper);
    }

    @Test
    void test_deserializeAList_withWrapper() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BusinessService business = new BusinessService();
        business.wrapper = objectMapper.readValue(new File("src/main/java/com/imp_exp/refact/dataLayer/allDocuments.json"), DocumentWrapper.class);
        business.documents = business.wrapper.documents;
        business.showDocuments();
    }

    // new serializing via wrapper
    @Test
    void test_serializeAllDocuments() throws IOException {
        BusinessService business = new BusinessService();
        serializer.serializeAllDocuments(business.wrapper);
    }

    @Test
    void test_deserializeDocuments() throws IOException {
        BusinessService business = new BusinessService();
        business.wrapper = serializer.deserializeDocuments();
        business.documents = business.wrapper.documents;
        business.showDocuments();
    }

}