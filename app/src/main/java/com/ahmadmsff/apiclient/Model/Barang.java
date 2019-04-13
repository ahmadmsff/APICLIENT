package com.ahmadmsff.apiclient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Barang {
    @SerializedName("id_barang")
    @Expose
    private Integer idBarang;
    @SerializedName("nama_barang")
    @Expose
    private String namaBarang;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("image")
    @Expose
    private String image_barang;

    public Integer getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(Integer idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Integer getHarga() {return harga;}

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getImage_barang() {return image_barang;}

    public void setImage_barang(String image_barang) {this.image_barang = image_barang;}
}
