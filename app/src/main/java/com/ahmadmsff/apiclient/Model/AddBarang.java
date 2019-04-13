package com.ahmadmsff.apiclient.Model;

public class AddBarang {
    final String nama_barang;
    final Integer harga_barang;
    final String img;

    public AddBarang(String nama_barang, Integer harga_barang, String img) {
        this.nama_barang = nama_barang;
        this.harga_barang = harga_barang;
        this.img = img;
    }
}
