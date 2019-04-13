package com.ahmadmsff.apiclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmadmsff.apiclient.APIHelper.BaseAPIServices;
import com.ahmadmsff.apiclient.Model.AddBarang;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddActivity extends AppCompatActivity {

    EditText nama_barang, harga_barang;
    Button btn_tambah_barang;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        context = this;

        init();
    }

    private void init() {
        nama_barang = (EditText)  findViewById(R.id.text_nama_barang);
        harga_barang = (EditText)  findViewById(R.id.text_harga_barang);
        btn_tambah_barang = (Button) findViewById(R.id.btn_tambah_barang);

        btn_tambah_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama_barang.getText().equals("")) {
                    nama_barang.requestFocus();
                    Toast.makeText(context, "Nama barang belum diisi", Toast.LENGTH_SHORT).show();
                } else if (harga_barang.getText().equals("")) {
                    harga_barang.requestFocus();
                    Toast.makeText(context, "Harga barang belum diisi", Toast.LENGTH_SHORT).show();
                } else {
                    tambah_data();
                }
            }
        });
    }

    private void tambah_data() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.2:45455/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        BaseAPIServices baseAPIServices = retrofit.create(BaseAPIServices.class);

        Call<ResponseBody> call = baseAPIServices.addBarang(new AddBarang(nama_barang.getText().toString(), Integer.parseInt(harga_barang.getText().toString())));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getString("status").equals("Success")) {
                            Toast.makeText(context, "Menu " + nama_barang.getText().toString() + " berhasil di tambah", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "Errorrrrr " + response.body().toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.d("Error", e.toString());

                    } catch (IOException e) {
                        Log.e("Error", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MENU", "Gagal dihapus " + t.toString());
            }
        });
    }
}
