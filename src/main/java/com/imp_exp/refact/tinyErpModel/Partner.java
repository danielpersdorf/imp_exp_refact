package com.imp_exp.refact.tinyErpModel;

public class Partner {
    public int id;
    public String name;

    public Partner(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
