package com.example.sendmeal.dao;

import android.content.Context;

import androidx.room.Room;

import com.example.sendmeal.dao.database.AppDataBase;
import com.example.sendmeal.dao.database.ItemsPedidoDao;
import com.example.sendmeal.dao.database.ItemsPedidoDao;
import com.example.sendmeal.domain.ItemsPedido;

import java.util.List;

public class ItemsPedidoRepository {
    private static ItemsPedidoRepository _REPO = null;
    private ItemsPedidoDao itemsPedidoDao;

    private ItemsPedidoRepository(Context ctx){
        AppDataBase db = Room.databaseBuilder(ctx,
                AppDataBase.class, "sendmeal-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        itemsPedidoDao = db.itemsPedidoDao();
    }

    public static ItemsPedidoRepository getInstance(Context ctx){
        if(_REPO ==null){
            _REPO = new ItemsPedidoRepository(ctx);
        }
        return _REPO;
    }

    public void crearItemsPedido(ItemsPedido p){
        itemsPedidoDao.crearItemsPedido(p);
    }
    public void borrarItemsPedido(ItemsPedido p){
        itemsPedidoDao.borrarItemsPedido(p);
    }
    public void actualizarItemsPedido(ItemsPedido p){
        itemsPedidoDao.actualizarItemsPedido(p);
    }
    public List<ItemsPedido> buscarItemsDeUnPedido(Integer id){
        return itemsPedidoDao.buscarItemsDeUnPedido(id);
    }
    public List<ItemsPedido> buscarItemsPedido(){
        return itemsPedidoDao.buscarItemsPedido();
    }
}
