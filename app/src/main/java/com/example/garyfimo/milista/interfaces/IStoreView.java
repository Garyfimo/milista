package com.example.garyfimo.milista.interfaces;

import android.content.Context;

import com.example.garyfimo.milista.model.Tienda;

import java.util.ArrayList;

/**
 * Created by Garyfimo on 12/27/15.
 */
public interface IStoreView {

    void getStores(ArrayList<Tienda> tiendaList);
    Context getContext();

}
