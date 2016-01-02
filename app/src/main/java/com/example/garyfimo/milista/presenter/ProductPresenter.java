package com.example.garyfimo.milista.presenter;

import com.example.garyfimo.milista.interfaces.IProductView;
import com.example.garyfimo.milista.model.Producto;

import java.util.ArrayList;

/**
 * Created by Garyfimo on 12/27/15.
 */
public class ProductPresenter {

    IProductView iProductView;

    public ProductPresenter(IProductView iProductView) {
        this.iProductView = iProductView;
    }

    public void getProducts(){
        ArrayList<Producto> list = new ArrayList<Producto>();

        list.add(new Producto("Leche Gloria",3.5f,3.60));
        list.add(new Producto("Leche Laive",2.5f,3.60));
        list.add(new Producto("Leche Ideal",5.0f,3.60));
        list.add(new Producto("Leche Bella Holandesa",1.5f,6.60));
        list.add(new Producto("Leche DanLac",0.5f,2.60));

        iProductView.getProducts(list);
    }
}
