package com.example.garyfimo.milista.presenter;

import com.example.garyfimo.milista.interfaces.IStoreView;
import com.example.garyfimo.milista.model.Tienda;

import java.util.ArrayList;

/**
 * Created by Garyfimo on 12/27/15.
 */
public class StorePresenter {
    IStoreView iStoreView;

    public StorePresenter(IStoreView iStoreView) {
        this.iStoreView = iStoreView;
    }

    public void getStores(){
        ArrayList<Tienda> tiendas = new ArrayList<Tienda>();

        tiendas.add(new Tienda("Plaza Vea","Av. Guardia Civil Norte 2304"));
        tiendas.add(new Tienda("Tottus","Av. Guardia Civil Norte 2304"));
        tiendas.add(new Tienda("Metro","Av. Guardia Civil Norte 2304"));
        tiendas.add(new Tienda("Wong","Av. Guardia Civil Norte 2304"));
        tiendas.add(new Tienda("Wallmart","Av. Guardia Civil Norte 2304"));
        tiendas.add(new Tienda("Vivanda","Av. Guardia Civil Norte 2304"));
        tiendas.add(new Tienda("Mass","Av. Guardia Civil Norte 2304"));

        iStoreView.getStores(tiendas);
    }
}
