package com.ahmadmsff.apiclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadmsff.apiclient.APIHelper.BaseAPIServices;
import com.ahmadmsff.apiclient.Model.AddBarang;
import com.ahmadmsff.apiclient.Model.EditBarang;

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

public class EditActivity extends AppCompatActivity {

    EditText id_barang, nama_barang, harga_barang;
    Button btn_edit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        context = this;

        id_barang = (EditText) findViewById(R.id.edit_id_barang);
        nama_barang = (EditText)  findViewById(R.id.edit_nama_barang);
        harga_barang = (EditText)  findViewById(R.id.edit_harga_barang);
        btn_edit = (Button) findViewById(R.id.btn_edit_barang);

        Intent intent = getIntent();

        String harga = intent.getStringExtra("harga");
        id_barang.setText(intent.getStringExtra("id_barang"));
        nama_barang.setText(intent.getStringExtra("nama_barang"));
        harga_barang.setText(harga , TextView.BufferType.EDITABLE);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

    }

    private void edit() {
        Integer id = Integer.parseInt(id_barang.getText().toString());
        String nama = nama_barang.getText().toString();
        Integer harga = Integer.parseInt(harga_barang.getText().toString());

        if (nama_barang.equals("")) {
            nama_barang.requestFocus();
            Toast.makeText(context, "Nama barang tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (harga_barang.equals("")) {
            harga_barang.requestFocus();
            Toast.makeText(context, "Harga barang tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.100.2:45455/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            BaseAPIServices baseAPIServices = retrofit.create(BaseAPIServices.class);

            Call<ResponseBody> call = baseAPIServices.editBarang(id, new EditBarang(nama, harga));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getString("status").equals("Success")) {
                                Toast.makeText(context, "Menu " + nama_barang.getText().toString() + " berhasil diedit", Toast.LENGTH_SHORT).show();
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

}
