package com.rajbhog.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @Expose(serialize = false)
    @SerializedName("productObject")
    private Product product;
    @SerializedName("product")
    @Expose
    private int productid;
    @Expose
    private int quantityh;
    @Expose
    private int quantityf;
    @Expose
    private int order;

    Item(Product product, int quantityh, int quantityf) {
        this.product = product;
        this.productid = product.getId();
        this.quantityh = quantityh;
        this.quantityf = quantityf;
    }

    public Product getProduct() { return this.product; }

    boolean eqls(Product product)
    {
        return this.product.getId() == product.getId();
    }

    boolean eqls(Item item)
    {
        return this.product == item.getProduct();
    }

    public int getQuantityh() {
        return quantityh;
    }

    public int getQuantityf() {
        return quantityf;
    }

    public void setQuantityh(int quantityh) {
        this.quantityh = quantityh;
    }

    public void setQuantityf(int quantityf) {
        this.quantityf = quantityf;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
