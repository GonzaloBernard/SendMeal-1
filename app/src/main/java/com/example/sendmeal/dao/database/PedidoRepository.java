package com.example.sendmeal.dao.database;

import android.content.Context;
import androidx.room.Room;

import com.example.sendmeal.domain.Pedido;

import java.util.List;

public class PedidoRepository {

    private static PedidoRepository _REPO = null;
    private PedidoDao pedidoDao;

    private PedidoRepository(Context ctx){
        AppDataBase db = Room.databaseBuilder(ctx,
                AppDataBase.class, "sendmeal-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        pedidoDao = db.pedidoDao();
    }

    public static PedidoRepository getInstance(Context ctx){
        if(_REPO ==null){
            _REPO = new PedidoRepository(ctx);
        }
        return _REPO;
    }

    public void crearPedido(Pedido p){
        pedidoDao.crearPedido(p);
    }
    public void borrarPedido(Pedido p){
        pedidoDao.borrarPedido(p);
    }
    public void actualizarPedido(Pedido p){
        pedidoDao.actualizarPedido(p);
    }
    public Pedido buscarPedidoPorID(Integer id){
        return pedidoDao.buscarPedidoPorID(id);
    }
    public List<Pedido> buscarPedidos(){
        return pedidoDao.buscarPedidos();
    }
}