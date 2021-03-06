package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public class Order extends Document {

    public int id;
    public String name;
    public String objType;
    public Partner partner;
    public List<Item> items;


    public Order() {
        super( 0, "Order", "17", new Partner(), new ArrayList<Item>());
        this.id = 0;
        this.name = "Order";
        this.objType = "17";
        this.partner = new Partner();
        this.items = new ArrayList<Item>();
    }

    public Order(Partner partner) {
        super( 1, "Order", "17", partner, new ArrayList<Item>());
        this.id = 1;
        this.name = "Order";
        this.objType = "17";
        this.partner = partner;
        this.items = new ArrayList<Item>();
    }

    public Order(int id,  Partner partner) {
        super(id, "Order", "17", partner, new ArrayList<Item>());
        this.id = id;
        this.name = "Order";
        this.objType = "17";
        this.partner = partner;
        this.items = new ArrayList<Item>();
    }

    @JsonCreator
    public Order(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("objType") String objType, @JsonProperty("partner") Partner partner, @JsonProperty("items") List<Item> items) {
        super(id, "Order", "17", partner, items);
        this.id = id;
        this.name = "Order";
        this.objType = "17";
        this.partner = partner;
        this.items = items;
    }

    public void setID(int id) { this.id = id; }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public void setPositions(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", objType='" + objType + '\'' +
                ", partner=" + partner +
                ", items=" + items +
                '}';
    }
}
