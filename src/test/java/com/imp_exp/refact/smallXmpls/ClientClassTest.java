package com.imp_exp.refact.smallXmpls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


class ClientClassTest {

    @Test
    void test_serializeAList() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        ClientClass clientClass = new ClientClass();

        BaseClass class1 = new SubClassA(1, "Order");
        BaseClass class2 = new SubClassB(2, "Delivery");

        clientClass.list.add(class1);
        clientClass.list.add(class2);

        objectMapper.writeValue(new File("src/test/java/com/imp_exp/refact/smallXmpls/testXmpls.json"), clientClass.list);
    }

    @Test
    void test_serializeAList_and_deserializeAgain() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        ClientClass clientClass = new ClientClass();

        BaseClass class1 = new SubClassA(1, "Order");
        BaseClass class2 = new SubClassB(2, "Delivery");

        clientClass.list.add(class1);
        clientClass.list.add(class2);

        objectMapper.writeValue(new File("src/test/java/com/imp_exp/refact/smallXmpls/testXmpls_justTheList.json"), clientClass.list);

        // reset client
        clientClass = new ClientClass();

        clientClass.list = objectMapper.readValue(new File("src/test/java/com/imp_exp/refact/smallXmpls/testXmpls_justTheList.json"), ArrayList.class);

        System.out.println(clientClass.list.toString());
    }

    @Test
    void test_serializeAList_and_deserializeAgain_serializingWholeClient() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        ClientClass clientClass = new ClientClass();

        BaseClass class1 = new SubClassA(1, "Order");
        BaseClass class2 = new SubClassB(2, "Delivery");

        clientClass.list.add(class1);
        clientClass.list.add(class2);

        objectMapper.writeValue(new File("src/test/java/com/imp_exp/refact/smallXmpls/testXmpls_theWholeClient.json"), clientClass);

        // reset client
        clientClass = new ClientClass();

        clientClass = objectMapper.readValue(new File("src/test/java/com/imp_exp/refact/smallXmpls/testXmpls_theWholeClient.json"), ClientClass.class);

        System.out.println(clientClass.list.toString());

        // -> this finally gives us the     "@class" : "com.imp_exp.refact.smallXmpls.SubClassA",
    }
}