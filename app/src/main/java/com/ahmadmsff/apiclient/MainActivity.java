package com.ahmadmsff.apiclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmadmsff.apiclient.APIHelper.BaseAPIServices;
import com.ahmadmsff.apiclient.Adapter.BarangAdapter;
import com.ahmadmsff.apiclient.Model.Barang;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "AAAAAAAAAAAAAAAAAAAAAAA";

    private BarangAdapter barangAdapter;
    private ArrayList<Barang> barangs;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btn = (Button) findViewById(R.id.btn_tambah);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Log.d(TAG, "DIMULAIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        getData();

    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.2:45455/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaseAPIServices baseAPIServices = retrofit.create(BaseAPIServices.class);
        Call<List<Barang>> call = baseAPIServices.getBarang();
        call.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, response.body().toString());
                Log.d(TAG, response.body().get(0).getNamaBarang());
                if (response.isSuccessful() && response.body() != null) {
                    barangs = new ArrayList<>(response.body());
                    barangAdapter = new BarangAdapter(getApplicationContext(), barangs);
                    recyclerView.setAdapter(barangAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Oops!, Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showOptionMenu(View v) {

    }
}
