package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Order.class, name = "ORDER"),
        @JsonSubTypes.Type(value = Delivery.class, name = "DELIVERY"),
        @JsonSubTypes.Type(value = Invoice.class, name = "INVOICE")})
public abstract class Document {

    public int id;
    public String name;
    public String objType;
    public Partner partner;
    public List<Item> items = new ArrayList<Item>();

    public Document(int id, String name, String objType, Partner partner, List<Item> items) {
        this.id = id;
        this.name = name;
        this.objType = objType;
        this.partner = partner;
        this.items = items;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setPositions(List<Item> items) {
        this.items = items;
    }
}

class Invoice extends Document {

    public Invoice(int id, String name, String objType, Partner partner, List<Item> items) {
        super(id, name, objType, partner, items);
    }

    @Override
    public void setID(int i) { }
}

class Delivery extends Document {

    public Delivery(int id, String name, String objType, Partner partner, List<Item> items) {
        super(id, name, objType, partner, items);
    }

    @Override
    public void setID(int i) { }
}


