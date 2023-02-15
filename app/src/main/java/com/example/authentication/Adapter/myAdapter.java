package com.example.authentication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.authentication.productos;
import com.example.authentication.R;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {
    Context context;
    ArrayList<productos>list;

    public myAdapter(Context context, ArrayList<productos> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.productos_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        productos producto=list.get(position);
        holder.code.setText(producto.getNomproducto());
        holder.stock.setText(producto.getStockproducto() );
        holder.precioVenta.setText(producto.getPrecioproducto());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,code,stock,precioVenta;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvNombreProducto);
            code=itemView.findViewById(R.id.tvCodigoProducto);
            stock=itemView.findViewById(R.id.tvStockProductolist);
            precioVenta=itemView.findViewById(R.id.tvPrecioProductolista);
        }
    }
}
