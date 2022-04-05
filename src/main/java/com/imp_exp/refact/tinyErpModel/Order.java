package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Order implements Document {
    public int id;
    public String name;
    public String objType;
    public Partner partner;
    public List<Item> items;


    public Order() {
        this.id = BusinessService.documents.size() + 1;
        this.name = "Order";
        this.objType = "17";
        this.partner = new Partner();
        this.items = new ArrayList<Item>();
    }

    //@JsonCreator
    // public Order(@JsonProperty("partner") Partner partner) {
    public Order( Partner partner) {
        // this.id = BusinessService.documents.size() + 1;
        this.id = 2;
        this.name = "Order";
        this.objType = "17";
        this.partner = partner;
        this.items = new ArrayList<Item>();
    }

    //@JsonCreator
    //public Order(@JsonProperty("id") int id, @JsonProperty("partner") Partner partner) {
    public Order(int id, Partner partner) {
        this.id = id;
        this.name = "Order";
        this.objType = "17";
        this.partner = partner;
        this.items = new ArrayList<Item>();
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
