package com.imp_exp.refact.tinyErpModel;

import java.util.ArrayList;
import java.util.List;


public class BusinessService {
    public List<Partner> partners;
    public List<Document> documents;
    public List<Item> items;
    public BusinessService() {
        partners = new ArrayList<Partner>();
        partners.add(new Partner(1, "Mr.Spock"));
    }

    public void showPartners() {
        for (Partner partner : partners) { System.out.println(partner.id + " " + partner.name); }
    }
    public Boolean addPartner(String name) { return partners.add(new Partner(2, name)); }
}
