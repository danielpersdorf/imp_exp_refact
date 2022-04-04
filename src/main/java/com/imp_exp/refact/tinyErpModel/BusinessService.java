package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class BusinessService {

    public List<Partner> partners;
    public List<Document> documents;
    public List<Item> items;

    public Boolean partnerSerialized;
    public Boolean allPartnersSerialized;


    public BusinessService() {
        Partner partner = new Partner(1, "Mr.Spock");

        partners = new ArrayList<Partner>();
        partners.add(partner);

        try {
            partnerSerialized = serializePartner(partner);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            allPartnersSerialized = serializeAllPartners(partners);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showPartners() {
        for (Partner partner : partners) { System.out.println(partner.id + " " + partner.name); }
    }
    public Boolean addPartner(String name) {
        Partner partner = new Partner(2, name);
        return partners.add(partner);
    }

    private Boolean serializePartner(Partner partner) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("target/partner.json"), partner);
        return true;
    }

    private Boolean serializeAllPartners(List<Partner> partners) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("target/allPartners.json"), partners);
        return true;
    }

}
