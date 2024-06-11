package com.baeldung.ls.events.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Dress implements Serializable {
    public static final List<String> Type = Arrays.asList("t-shirt", "hat", "trousers", "sunglasses");
    public static final List<String> Size = Arrays.asList("small", "medium", "large");
    public static final List<String> Gender = Arrays.asList("man", "woman", "kid");
    private String type;
    private String gender;
    private String size;

    public Dress(){

    }

    public Dress(String type, String gender, String size) {
        this.type = type;
        this.gender = gender;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Dress{" + "type=" + type + ", gender=" + gender + ", size=" + size + '}';
    }
}
