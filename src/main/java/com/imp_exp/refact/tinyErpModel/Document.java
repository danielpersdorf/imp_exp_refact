package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        //use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        //property = "type")
        property = "@class",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Order.class),
        @JsonSubTypes.Type(value = Delivery.class),
        @JsonSubTypes.Type(value = Invoice.class)})
public abstract class Document {

    public int id;
    public String name;
    public String objType;
    public Partner partner;
    // public List<Item> items = new ArrayList<Item>();

    public Document(int id, String name, String objType, Partner partner) {
        this.id = id;
        this.name = name;
        this.objType = objType;
        this.partner = partner;
        // this.items = items;
    }

    public void setID(int i) {};

    public void setPositions(List<Item> items) {
        //this.items = items;
    }
}

class Invoice extends Document {

    public Invoice(int id, String name, String objType, Partner partner, List<Item> items) {
        super(id, name, objType, partner);
    }

    @Override
    public void setID(int i) { }
}

class Delivery extends Document {

    public Delivery(int id, String name, String objType, Partner partner, List<Item> items) {
        super(id, name, objType, partner);
    }

    @Override
    public void setID(int i) { }
}


