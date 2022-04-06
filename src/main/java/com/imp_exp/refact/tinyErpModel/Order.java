package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@class",
//        visible = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = Order.class, name = "subA")})
public class Order extends Document {

    public int id;
    public String name;
    public String objType;
    public Partner partner;
    // public List<Item> items;


    // @JsonCreator
    public Order() {
        super( 0, "Order", "17", new Partner());
        // this.id = BusinessService.documents.size() + 1;
        this.id = 0;
        this.name = "Order";
        this.objType = "17";
        this.partner = new Partner();
        // this.items = new ArrayList<Item>();
    }

    //@JsonCreator
    // public Order(@JsonProperty("partner") Partner partner) {
    public Order(Partner partner) {
        super( 1, "Order", "17", partner);
        this.id = 1;
        this.name = "Order";
        this.objType = "17";
        this.partner = partner;
    }

    //@JsonCreator
    //public Order(@JsonProperty("id") int id, @JsonProperty("partner") Partner partner) {
        public Order(int id,  Partner partner) {
        super(id, "Order", "17", partner);
        // this.id = BusinessService.documents.size() + 1;
        this.id = id;
        this.name = "Order";
        this.objType = "17";
        this.partner = partner;
        // this.items = new ArrayList<Item>();
    }

    // @JsonCreator
    // public Order(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("objType") String objType, @JsonProperty("partner") Partner partner) {
    public Order(int id, String name, String objType, Partner partner) {
        super(id, "Order", "17", partner);
        this.id = id;
        this.name = "Order";
        this.objType = "17";
        this.partner = partner;
        // this.items = new ArrayList<Item>();
    }

    public void setID(int id) { this.id = id; }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public void setPositions(List<Item> items) {
        //this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", objType='" + objType + '\'' +
                ", partner=" + partner +
                // ", items=" + items +
                '}';
    }
}
