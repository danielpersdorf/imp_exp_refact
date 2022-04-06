package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

//@JsonSubTypes({ @JsonSubTypes.Type(value = Item.class, name = "items") })
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.CLASS,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "class",
//        visible = true)
public class Item {
    public int id;
    public String name;

    //@JsonCreator
    public Item() {}

    //@JsonCreator
    // public Item(@JsonProperty("name") String name) {
    public Item(String name) {
        this.name = name;
    }

    // @JsonCreator
    //public Item(@JsonProperty("id") int id, @JsonProperty("name") String name) {
    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
