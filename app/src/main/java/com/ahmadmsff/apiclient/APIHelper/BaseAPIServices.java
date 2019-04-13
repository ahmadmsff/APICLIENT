package com.ahmadmsff.apiclient.APIHelper;

import com.ahmadmsff.apiclient.Model.AddBarang;
import com.ahmadmsff.apiclient.Model.Barang;
import com.ahmadmsff.apiclient.Model.EditBarang;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BaseAPIServices {

    @POST("values")
    Call<ResponseBody> addBarang(@Body AddBarang body);

    @GET("values")
    Call<List<Barang>> getBarang();

    @PUT("values/{id}")
    Call<ResponseBody> editBarang(@Path("id") Integer id,
                                  @Body EditBarang body);

    @DELETE("values/{id}")
    Call<ResponseBody> deleteBarang(@Path("id") Integer id);
}
