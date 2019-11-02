package com.example.sendmeal.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.sendmeal.domain.PedidoYTodosSusItems;

@Dao
public interface PedidosEItemsPedidosDao {

    @Query("SELECT * FROM pedido WHERE id LIKE :id_pedido")
    PedidoYTodosSusItems getPedidoAndAllItems(Integer id_pedido);
}
