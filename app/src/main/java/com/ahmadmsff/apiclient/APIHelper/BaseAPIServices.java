package com.ahmadmsff.apiclient.APIHelper;

import com.ahmadmsff.apiclient.Model.Barang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BaseAPIServices {
    @GET("values")
    Call<List<Barang>> getBarang();
}
