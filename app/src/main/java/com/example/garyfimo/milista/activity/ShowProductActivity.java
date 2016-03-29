package com.example.garyfimo.milista.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.garyfimo.milista.R;
import com.example.garyfimo.milista.adapter.ProductoAdapter;
import com.example.garyfimo.milista.interfaces.IProductView;
import com.example.garyfimo.milista.model.Producto;
import com.example.garyfimo.milista.model.Tienda;
import com.example.garyfimo.milista.presenter.ProductPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowProductActivity extends AppCompatActivity implements IProductView, SearchView.OnQueryTextListener {

    @Bind(R.id.rv_products)
    RecyclerView rvProducts;

    ProductPresenter productPresenter;
    ArrayList<Producto> productList;
    ProductoAdapter productoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(ShowProductActivity.this);

        productList = new ArrayList<Producto>();
        productPresenter = new ProductPresenter(ShowProductActivity.this);
        productPresenter.getProducts();


//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.num_rows));
//        rvProducts.setLayoutManager(layoutManager);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                getResources().getInteger(R.integer.num_rows) , GridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvProducts.setLayoutManager(staggeredGridLayoutManager);


        productoAdapter = new ProductoAdapter(productList, new ProductoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto producto) {

            }
        });

        rvProducts.setAdapter(productoAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void getProducts(ArrayList<Producto> productos) {
        productList = productos;
    }

    @Override
    public Context getContext() {
        return ShowProductActivity.this;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
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
    public boolean onQueryTextChange(String newText) {
        final List<Producto> filteredModelList = filter(productList, newText);
        productoAdapter.animateTo(filteredModelList);
        rvProducts.scrollToPosition(0);
        return true;
    }

    private List<Producto> filter(List<Producto> models, String query) {
        query = query.toLowerCase();

        List<Producto> filteredModelList = new ArrayList<>();
        for (Producto model : models) {
            final String text = model.getProductName().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}