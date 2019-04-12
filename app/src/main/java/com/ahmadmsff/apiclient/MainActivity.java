package com.ahmadmsff.apiclient;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ahmadmsff.apiclient.APIHelper.APIClient;
import com.ahmadmsff.apiclient.APIHelper.BaseAPIServices;
import com.ahmadmsff.apiclient.Adapter.BarangAdapter;
import com.ahmadmsff.apiclient.Model.Barang;
import com.ahmadmsff.apiclient.Model.GetBarang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    List<Barang> barangs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.2:45455/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseAPIServices api = retrofit.create(BaseAPIServices.class);
        api.getBarang().enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
//                Log.d("RETROFIT", response.body().toString());
                showData(response.body());
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                Log.d("RETROFIT", "onFailure: ");
            }
        });


    }

    private void showData(List<Barang> barang) {
        adapter = new BarangAdapter(barang);
        recyclerView.setAdapter(adapter);
    }
}
