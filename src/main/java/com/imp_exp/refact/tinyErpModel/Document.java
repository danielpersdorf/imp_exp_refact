package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

public interface Document {
    int id = 0;
    String name = null;
    String objType = null;
    Partner partner = null;
    List<Item> items = new ArrayList<Item>();

}

class Invoice implements Document { }

class Delivery implements Document { }


