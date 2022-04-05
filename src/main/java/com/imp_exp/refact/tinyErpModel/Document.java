package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

public interface Document {

    String name = null;
    String objType = null;
    Partner partner = null;
    List<Item> items = new ArrayList<Item>();

    void setID(int i);
}

class Invoice implements Document {
    @Override
    public void setID(int i) { }
}

class Delivery implements Document {
    @Override
    public void setID(int i) { }
}


