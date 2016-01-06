package com.example.garyfimo.milista.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.garyfimo.milista.R;
import com.example.garyfimo.milista.adapter.TiendaAdapter;
import com.example.garyfimo.milista.interfaces.IStoreView;
import com.example.garyfimo.milista.model.Tienda;
import com.example.garyfimo.milista.presenter.StorePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IStoreView, SearchView.OnQueryTextListener {

    @Bind(R.id.rv_tiendas)
    RecyclerView rvTiendas;

    List<Tienda> listaTiendas;
    StorePresenter storePresenter;
    TiendaAdapter tiendaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(MainActivity.this);

        listaTiendas = new ArrayList<Tienda>();

        storePresenter = new StorePresenter(MainActivity.this);
        storePresenter.getStores();

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//        rvTiendas.setLayoutManager(linearLayoutManager);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        rvTiendas.setLayoutManager(layoutManager);

        tiendaAdapter = new TiendaAdapter(listaTiendas, new TiendaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tienda tienda) {
                startActivity(new Intent(MainActivity.this, LocationStoreActivity.class));
            }
        });
        rvTiendas.setAdapter(tiendaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getStores(ArrayList<Tienda> tiendaList) {
        this.listaTiendas = tiendaList;
    }

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Tienda> filteredModelList = filter(listaTiendas, newText);
        tiendaAdapter.animateTo(filteredModelList);
        rvTiendas.scrollToPosition(0);
        return true;
    }

    private List<Tienda> filter(List<Tienda> models, String query) {
        query = query.toLowerCase();

        List<Tienda> filteredModelList = new ArrayList<>();
        for (Tienda model : models) {
            final String text = model.getStoreName().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
