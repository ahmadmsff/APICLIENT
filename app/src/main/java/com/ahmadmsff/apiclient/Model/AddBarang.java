package com.ahmadmsff.apiclient.Model;

public class AddBarang {
    final String nama_barang;
    final Integer harga;
    final String image;

    public AddBarang(String nama_barang, Integer harga_barang, String img) {
        this.nama_barang = nama_barang;
        this.harga = harga_barang;
        this.image = img;
    }
}
