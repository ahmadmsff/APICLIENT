package com.ahmadmsff.apiclient.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmadmsff.apiclient.Model.Barang;
import com.ahmadmsff.apiclient.Model.GetBarang;
import com.ahmadmsff.apiclient.R;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.MyViewHolder> {

    private List<Barang> barang;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nama_barang, harga_barang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_barang = (TextView) itemView.findViewById(R.id.nama_barang);
            harga_barang = (TextView) itemView.findViewById(R.id.harga_barang);
        }
    }

    public BarangAdapter (List<Barang> barang) {
        this.barang = barang;
    }

    @NonNull
    @Override
    public BarangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_barang, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.nama_barang.setText(barang.get(i).getNama_barang());
        myViewHolder.harga_barang.setText(barang.get(i).getHarga());
    }

    @Override
    public int getItemCount() {
        return barang.size();
    }
}
