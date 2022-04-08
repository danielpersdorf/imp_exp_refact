package com.imp_exp.refact.tinyErpModel;

import java.util.ArrayList;
import java.util.List;

public class DocumentWrapper {
    public List<Document> documents;

    public DocumentWrapper() {
        this.documents = new ArrayList<Document>();
    }

    public DocumentWrapper(List list) {
        this.documents = list;
    }
}
