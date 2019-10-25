package com.example.sendmeal.dao;


import android.util.Log;
import com.example.sendmeal.dao.rest.PlatoRest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//SINGLETON
//https://www.youtube.com/watch?v=Z06Phxtj13g <-- tutorial

public class PlatorRepository {

    public static String _SERVER = "http://10.0.2.2:3000/"; //esto tengo que cambiarlo pero ni idea de como

    private static PlatorRepository _INSTANCE;

    private Retrofit rf;
    private PlatoRest platoRest;

    private PlatorRepository(){} //constructor privado, vacio

    public static PlatorRepository getInstance(){

        if(_INSTANCE == null){
            _INSTANCE = new PlatorRepository();
            _INSTANCE.configurarRetrofit();

        }

        return  _INSTANCE;
    }

    private void configurarRetrofit() {
        new Retrofit.Builder()
                .baseUrl(_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP_2","INSTANCIA CREADA");
        this.platoRest = this.rf.create(PlatoRest.class);
    }
}

