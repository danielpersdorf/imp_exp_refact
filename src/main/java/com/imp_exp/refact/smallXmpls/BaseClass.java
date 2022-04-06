package com.imp_exp.refact.smallXmpls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SubClassA.class),
        @JsonSubTypes.Type(value = SubClassB.class)})
public abstract class BaseClass {
    public int id;
    public String name;

    public BaseClass(int id, String name) {
        this.id = id;
        this.name = name;
    }
}


class SubClassA extends BaseClass {
    @JsonCreator
    public SubClassA(@JsonProperty("id")int id, @JsonProperty("name")String name) {
        super(id, name);
    }
}

class SubClassB extends BaseClass {
    @JsonCreator
    public SubClassB(@JsonProperty("id")int id, @JsonProperty("name")String name) {
        super(id, name);
    }
}