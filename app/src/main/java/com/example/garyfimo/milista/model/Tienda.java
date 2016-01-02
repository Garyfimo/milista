package com.example.garyfimo.milista.model;

/**
 * Created by Garyfimo on 12/27/15.
 */
public class Tienda {

    private int id;
    private String storeName;
    private double storeLat;
    private double storeLon;
    private String storeAddress;

    public Tienda() {
    }

    public Tienda(String storeName, String storeAddress) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getStoreLat() {
        return storeLat;
    }

    public void setStoreLat(double storeLat) {
        this.storeLat = storeLat;
    }

    public double getStoreLon() {
        return storeLon;
    }

    public void setStoreLon(double storeLon) {
        this.storeLon = storeLon;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
}
