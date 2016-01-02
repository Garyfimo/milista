package com.example.garyfimo.milista.model;

/**
 * Created by Garyfimo on 12/27/15.
 */
public class Producto {

    private int id;
    private String productName;
    private double productPrice;
    private String productBarcode;
    private float productRate;

    public Producto() {

    }

    public Producto(String productName, float productRate, double productPrice) {
        this.productName = productName;
        this.productRate = productRate;
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public float getProductRate() {
        return productRate;
    }

    public void setProductRate(float productRate) {
        this.productRate = productRate;
    }
}
