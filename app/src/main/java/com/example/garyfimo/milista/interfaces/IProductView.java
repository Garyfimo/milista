package com.example.garyfimo.milista.interfaces;

import android.content.Context;

import com.example.garyfimo.milista.model.Producto;

import java.util.ArrayList;

/**
 * Created by Garyfimo on 12/27/15.
 */
public interface IProductView {

    void getProducts(ArrayList<Producto> productos);
    Context getContext();
}
