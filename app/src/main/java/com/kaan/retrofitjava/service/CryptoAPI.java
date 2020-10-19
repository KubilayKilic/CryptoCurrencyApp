package com.kaan.retrofitjava.service;

import com.kaan.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

//https://api.nomics.com/v1/prices?key=d4571322e0fe399f9ba9ec3233abb8a1


    @GET("prices?key=d4571322e0fe399f9ba9ec3233abb8a1")
    Observable<List<CryptoModel>> getData();


    //Call<List<CryptoModel>> getData();

    //verilen link'e get isteği yolla, async call ile liste içerisinde crypto modeli getir, buna da getData de.



}
