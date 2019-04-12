package com.ahmadmsff.apiclient.Model;

import com.google.gson.annotations.SerializedName;

public class Barang {
    @SerializedName("id_barang")
    private int id_barang;
    @SerializedName("nama_barang")
    private String nama_barang;
    @SerializedName("harga")
    private int harga;

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
