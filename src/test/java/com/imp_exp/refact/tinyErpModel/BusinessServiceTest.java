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
    void test_createFirstDocumentEntriesInDB() throws IOException {
        // let`s create the first entries in DB (json)
        // init business
        BusinessService business = new BusinessService();
        // create a partner
        Partner partner = new Partner("Customer 0");
        // create a document (order)
        Order order = new Order(0, partner);
        // create items
        Item item0 = new Item(0,"Test Item 0");
        Item item1 = new Item(1,"Test Item 1");
        Item item2 = new Item(2,"Test Item 2");
        Item item3 = new Item(3,"Test Item 3");
        Item item4 = new Item(4,"Test Item 4");
        // create list of items
        List<Item> newItems = new ArrayList<Item>();

        // populate list with items
        newItems.add(item0);
        newItems.add(item1);
        newItems.add(item2);
        newItems.add(item3);
        newItems.add(item4);

        // setPositions on order list
        order.setPositions(newItems);

        // create the list of docs
        BusinessService.documents = new ArrayList<Document>();

        // add the doc to documents
        business.addDocument(order);
        business.showDocuments();
    }

    // partners
    @Test
    void testShowPartners() {
    }

    @Test
    void test_addPartner() {
    }

    @Test
    void test_serializeAllPartners() {
    }

    @Test
    void test_deserializePartners() {
    }

    // docs
    @Test
    void test_showDocuments() {
    }

    @Test
    void test_addDocument() {
    }

    @Test
    void test_serializeAllDocuments() {
    }

    @Test
    void test_deserializeDocuments() {
    }
}