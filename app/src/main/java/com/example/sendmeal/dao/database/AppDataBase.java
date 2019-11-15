package com.example.sendmeal.dao.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sendmeal.domain.Pedido;
import com.example.sendmeal.domain.ItemsPedido;

@Database(entities = {Pedido.class, ItemsPedido.class} , version = 9)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract PedidoDao pedidoDao();
    public abstract ItemsPedidoDao itemsPedidoDao();
    /*public abstract PedidosEItemsPedidosDao pedidosEItemsPedidosDao();*/
}
