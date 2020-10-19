package com.kaan.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaan.retrofitjava.R;
import com.kaan.retrofitjava.adapter.RecyclerViewAdapter;
import com.kaan.retrofitjava.model.CryptoModel;
import com.kaan.retrofitjava.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerview;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = findViewById(R.id.recyclerView);
        //modeli oluşturmak.
        // currency ve price değişkenleri olan bir sınıf oluşuturup bu modele atayarak bir model oluşturmak.

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();
    }

    private void loadData() {

        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

        /*

        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView
                    recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerview.setAdapter((recyclerViewAdapter));

                    for (CryptoModel cryptoModel : cryptoModels ) {
                        System.out.println(cryptoModel.currency);

                    }

                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

*/

    }

    private void handleResponse(List<CryptoModel> cryptoModelList){
        cryptoModels = new ArrayList<>(cryptoModelList);

        //RecyclerView
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
        recyclerview.setAdapter((recyclerViewAdapter));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}


// d4571322e0fe399f9ba9ec3233abb8a1
//https://api.nomics.com/v1/prices?key=d4571322e0fe399f9ba9ec3233abb8a1