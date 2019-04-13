package com.ahmadmsff.apiclient.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadmsff.apiclient.APIHelper.BaseAPIServices;
import com.ahmadmsff.apiclient.EditActivity;
import com.ahmadmsff.apiclient.Model.Barang;
import com.ahmadmsff.apiclient.Model.EditBarang;
import com.ahmadmsff.apiclient.R;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.MyViewHolder> {

    private ArrayList<Barang> barang;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nama_barang, harga_barang;
        public ImageView image_barang, options_menu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_barang = (ImageView) itemView.findViewById(R.id.image_barang);
            nama_barang = (TextView) itemView.findViewById(R.id.nama_barang);
            harga_barang = (TextView) itemView.findViewById(R.id.harga_barang);
            options_menu = (ImageView) itemView.findViewById(R.id.btn_options);

        }
    }

    public BarangAdapter (Context context, ArrayList<Barang> barang) {
        this.context = context;
        this.barang = barang;
    }

    @NonNull
    @Override
    public BarangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_barang, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BarangAdapter.MyViewHolder myViewHolder, final int i) {
        final String image = this.barang.get(i).getImage_barang();
        final Integer id = this.barang.get(i).getIdBarang();
        final String nama = this.barang.get(i).getNamaBarang();
        final Integer harga = this.barang.get(i).getHarga();

        Glide.with(context)
                .load("http://192.168.100.2:45455/" + image)
                .fitCenter()
                .into(myViewHolder.image_barang);

        myViewHolder.nama_barang.setText(nama);
        myViewHolder.harga_barang.setText(harga.toString());
        myViewHolder.options_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, myViewHolder.options_menu);
                popupMenu.inflate(R.menu.options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                Intent intent = new Intent(context, EditActivity.class);
                                intent.putExtra("id_barang", id.toString());
                                intent.putExtra("nama_barang", nama);
                                intent.putExtra("harga", harga.toString());
                                context.startActivity(intent);
                                break;
                            case R.id.menu_delete:

                                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                                alertbox.setMessage("Yakin ingin menghapus barang " + nama.toString() + "?");
                                alertbox.setTitle("Confirmation");
                                alertbox.setIcon(R.drawable.ic_warning);

                                alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                                        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl("http://192.168.100.2:45455/api/")
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .client(client)
                                                .build();

                                        BaseAPIServices baseAPIServices = retrofit.create(BaseAPIServices.class);
                                        Call<ResponseBody> call = baseAPIServices.deleteBarang(id);
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    try {
                                                        String result = response.body().string();
                                                        JSONObject jsonObject = new JSONObject(result);
                                                        if (jsonObject.getString("status").equals("Success")) {
                                                            barang.remove(i);
                                                            notifyDataSetChanged();
                                                            Toast.makeText(context, "Menu " + nama + " berhasil di hapus", Toast.LENGTH_SHORT).show();
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
                                });
                                alertbox.setNegativeButton("No", null).show();


                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return barang.size();
    }
}
