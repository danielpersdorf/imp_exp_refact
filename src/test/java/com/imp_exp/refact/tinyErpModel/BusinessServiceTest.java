package com.imp_exp.refact.tinyErpModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class BusinessServiceTest {

    @Test
    void test_initialize_new_BusinessService() throws IOException {
        BusinessService business = new BusinessService();
        assertNotNull(business);
    }

    @Test
    void test_showPartners() throws IOException {
        BusinessService business = new BusinessService();
        business.showPartners();
    }

    @Test
    void test_showItems() throws IOException {
        BusinessService business = new BusinessService();
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


    @Test
    void test_showDocuments() throws IOException {
        BusinessService business = new BusinessService();
        business.showDocuments();
    }

    @Test
    void test_addDocumentEntriesInDB_withoutItems() throws IOException {
        // let`s create the first entries in DB (json)
        // init business
        BusinessService business = new BusinessService();
        // create a partner
        Partner partner = new Partner("Customer 3");
        // create a document (order)
        Document document = new Order(3, partner);
        // add the doc to documents
        business.addDocument(document);
        business.showDocuments();
    }


    // now with items on documents
    @Test
    void test_addDocumentEntriesInDB() throws IOException {
        // let`s create the first entries in DB (json)
        // init business
        BusinessService business = new BusinessService();
        // create a partner
        Partner partner = new Partner("Customer 0");
        // create a document (order)
        Document document = new Order(0, partner);
        // create list of items
        List<Item> newItems = new ArrayList<Item>();
        // create items
        Item item0 = new Item(0,"Test Item 0");
        Item item1 = new Item(1,"Test Item 1");
        Item item2 = new Item(2,"Test Item 2");
        Item item3 = new Item(3,"Test Item 3");
        Item item4 = new Item(4,"Test Item 4");
        // populate list with items
        newItems.add(item0);
        newItems.add(item1);
        newItems.add(item2);
        newItems.add(item3);
        newItems.add(item4);

        // setPositions on order list
        document.setPositions(newItems);

        // create the list of docs
        // business.documents = new ArrayList<Document>();
        // List already there

        // add the doc to documents
        business.addDocument(document);
        business.showDocuments();
    }

}