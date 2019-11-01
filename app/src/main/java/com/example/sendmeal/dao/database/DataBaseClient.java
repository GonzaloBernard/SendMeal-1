package com.example.sendmeal.dao.database;

import android.content.Context;

import androidx.room.Room;

import com.example.sendmeal.dao.AppDataBase;

public class DataBaseClient {
    private static DataBaseClient DB = null;

    private AppDataBase pedidoDataBase;

    private DataBaseClient(Context ctx){
        pedidoDataBase = Room.databaseBuilder(ctx,
                AppDataBase.class, "sendmeal-db").allowMainThreadQueries().build();
    }

    public synchronized static DataBaseClient getInstance(Context ctx){
        if(DB==null){
            DB = new DataBaseClient(ctx);
        }
        return DB;
    }

    public AppDataBase getAppDataBase() {
        return pedidoDataBase;
    }
}