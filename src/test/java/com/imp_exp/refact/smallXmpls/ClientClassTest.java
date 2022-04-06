package com.imp_exp.refact.smallXmpls;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.imp_exp.refact.tinyErpModel.Item;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        objectMapper.writeValue(new File("src/test/java/com/imp_exp/refact/smallXmpls/testXmpls.json"), clientClass);

        clientClass = null;

        clientClass = objectMapper.readValue(new File("src/test/java/com/imp_exp/refact/smallXmpls/testXmpls.json"), ClientClass.class);

        System.out.println(clientClass.list.toString());

    }
}