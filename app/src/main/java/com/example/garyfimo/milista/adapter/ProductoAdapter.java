package com.example.garyfimo.milista.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.garyfimo.milista.R;
import com.example.garyfimo.milista.model.Producto;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Garyfimo on 12/27/15.
 */
public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> productoList;
    private OnItemClickListener listener;

    public ProductoAdapter(List<Producto> productoList, OnItemClickListener listener) {
        this.productoList = new ArrayList<>(productoList);
        this.listener = listener;
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_card_view, parent, false);
        ProductoViewHolder productoViewHolder = new ProductoViewHolder(view);
        return productoViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        holder.bind(productoList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }


    public static class ProductoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_producto)
        ImageView ivProducto;

        @Bind(R.id.rb_producto_rate)
        RatingBar rbProductoRate;

        @Bind(R.id.tv_producto_name)
        TextView tvProductoName;

        @Bind(R.id.tv_producto_price)
        TextView tvProductoPrice;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Producto producto, final OnItemClickListener listener) {
            tvProductoName.setText(producto.getProductName());
            tvProductoPrice.setText(String.format("Precio: S/ " + String.valueOf(producto.getProductPrice())));
            rbProductoRate.setRating(producto.getProductRate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(producto);
                }
            });
        }
    }


    public Producto removeItem(int position) {
        final Producto model = productoList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Producto model) {
        productoList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Producto model = productoList.remove(fromPosition);
        productoList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Producto> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Producto> newModels) {
        for (int i = productoList.size() - 1; i >= 0; i--) {
            final Producto model = productoList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Producto> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Producto model = newModels.get(i);
            if (!productoList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Producto> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Producto model = newModels.get(toPosition);
            final int fromPosition = productoList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Producto producto);
    }
}
