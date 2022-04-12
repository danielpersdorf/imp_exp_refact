package com.imp_exp.refact.tinyErpModel;


import java.io.IOException;

public class Item {
    public int id;
    public String name;

    public Item() {}

    public Item(String name) {
        this.name = name;
    }

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

    public boolean addItemToList() throws IOException {
        return BusinessService.addItem(BusinessService.oItemCompany);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
