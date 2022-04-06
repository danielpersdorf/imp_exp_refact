package com.imp_exp.refact.smallXmpls;

import java.util.ArrayList;
import java.util.List;

public class ClientClass {

    public List<BaseClass> list;

    public ClientClass() {
        this.list = new ArrayList<BaseClass>();
    }

    public ClientClass(List list) {
        this.list = list;
    }

}
