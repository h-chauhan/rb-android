package com.rajbhog.POJO;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int priceh;
    private int pricef;
    private int category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceh() {
        return priceh;
    }

    public void setPriceh(int priceh) {
        this.priceh = priceh;
    }

    public int getPricef() {
        return pricef;
    }

    public void setPricef(int pricef) {
        this.pricef = pricef;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
