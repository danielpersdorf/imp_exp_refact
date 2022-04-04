package com.imp_exp.refact.tinyErpModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class BusinessService {

    private List<Partner> partners;
    private List<Document> documents;
    private List<Item> items;

    ObjectMapper objectMapper = new ObjectMapper();

    private Boolean partnerAdded;
    private Boolean allPartnersSerialized;

    public BusinessService() throws IOException {

        partners = deserializePartners();

    }

    public void showPartners() {
        for (Partner partner : partners) { System.out.println(partner.id + " " + partner.name); }
    }

    public Boolean addPartner(String name) throws IOException {
        int nextCode = partners.size() + 1;
        Partner partner = new Partner(nextCode , name + " " + nextCode);
        partnerAdded = partners.add(partner);
        allPartnersSerialized = serializeAllPartners(partners);
        return partnerAdded & allPartnersSerialized;
    }

    private Boolean serializeAllPartners(List<Partner> partners) throws IOException {
        objectMapper.writeValue(new File("src/main/resources/allPartner.json"), partners);
        return true;
    }

    private List<Partner> deserializePartners() throws IOException {
        return objectMapper.readValue(new File("src/main/resources/allPartner.json"), new TypeReference<List<Partner>>(){});
    }
}
