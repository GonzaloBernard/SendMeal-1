package com.example.sendmeal.dao.database;

import com.example.sendmeal.domain.ItemsPedido;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface ItemsPedidoDao {

    @Query("SELECT * FROM itemsPedido")
    List<ItemsPedido> getAll();

    @Insert
    void insert(ItemsPedido itemsPedido);

    @Insert
    void insertAll(ItemsPedido... itemsPedido);

    @Delete
    void delete(ItemsPedido itemsPedido);

    @Update
    void actualizar(ItemsPedido itemsPedido);
}
