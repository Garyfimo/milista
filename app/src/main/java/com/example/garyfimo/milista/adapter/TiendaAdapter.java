package com.example.garyfimo.milista.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garyfimo.milista.R;
import com.example.garyfimo.milista.model.Tienda;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Garyfimo on 12/27/15.
 */
public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.TiendaViewHolder> {


    private List<Tienda> tiendaList;
    private OnItemClickListener listener;

    public TiendaAdapter(List<Tienda> tiendaList, OnItemClickListener listener) {
        this.tiendaList = new ArrayList<>(tiendaList);
        this.listener = listener;
    }

    @Override
    public TiendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tienda_card_view, parent, false);
        TiendaViewHolder tiendaViewHolder = new TiendaViewHolder(view);
        return tiendaViewHolder;
    }

    @Override
    public void onBindViewHolder(TiendaViewHolder holder, int position) {
        holder.bind(tiendaList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return tiendaList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class TiendaViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.cv_tienda)
        CardView cvTienda;

        @Bind(R.id.tv_store_name)
        TextView tvStoreName;

        @Bind(R.id.tv_store_address)
        TextView tvStoreAddress;


        public TiendaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Tienda tienda, final OnItemClickListener listener){
            tvStoreAddress.setText(tienda.getStoreAddress());
            tvStoreName.setText(tienda.getStoreName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(tienda);
                }
            });
        }

    }

    public interface OnItemClickListener{

        void onItemClick(Tienda tienda);

    }

    public Tienda removeItem(int position){
        final Tienda model = tiendaList.remove(position);
        notifyItemRemoved(position);
        return  model;
    }

    public void addItem(int position, Tienda model) {
        tiendaList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Tienda model = tiendaList.remove(fromPosition);
        tiendaList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Tienda> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Tienda> newModels) {
        for (int i = tiendaList.size() - 1; i >= 0; i--) {
            final Tienda model = tiendaList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Tienda> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Tienda model = newModels.get(i);
            if (!tiendaList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Tienda> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Tienda model = newModels.get(toPosition);
            final int fromPosition = tiendaList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


}
