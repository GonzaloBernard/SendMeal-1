package com.example.sendmeal.dao.database;

import com.example.sendmeal.domain.Pedido;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PedidoDao {
    @Insert
    void crearPedido(Pedido pedido);

    @Delete
    void borrarPedido(Pedido pedido);

    @Update
    void actualizarPedido(Pedido pedido);

    @Query("SELECT * FROM pedido")
    List<Pedido> buscarPedidos();

    @Query("SELECT * FROM Pedido WHERE id = :id")
    Pedido buscarPedidoPorID(Integer id);
}