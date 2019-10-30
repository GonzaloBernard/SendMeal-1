package com.example.sendmeal.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sendmeal.domain.Pedido;
import com.example.sendmeal.domain.ItemsPedido;

@Database(entities = {Pedido.class, ItemsPedido.class} , version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract PedidoDao pedidoDao(); //que permisos va a tener
    public abstract ItemsPedido itemsPedido();
}
