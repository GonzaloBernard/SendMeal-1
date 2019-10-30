package com.example.sendmeal.dao;

import com.example.sendmeal.domain.Pedido;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PedidoDao {
    @Query("SELECT * FROM pedido")
    List<Pedido> getAll();

    @Insert
    void insert(Pedido pedido);

    @Insert
    void insertAll(Pedido... pedidos);

    @Delete
    void delete(Pedido pedido);

    @Update
    void actualizar(Pedido pedido);
}
