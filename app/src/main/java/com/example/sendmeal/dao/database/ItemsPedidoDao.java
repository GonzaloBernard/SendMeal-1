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

    @Insert
    void crearItemsPedido(ItemsPedido itemsPedido);

    @Delete
    void borrarItemsPedido(ItemsPedido itemsPedido);

    @Update
    void actualizarItemsPedido(ItemsPedido itemsPedido);

    @Query("SELECT * FROM ItemsPedido")
    List<ItemsPedido> buscarItemsPedido();

    // ESTO DEBERIA ESTAR IMPLEMETADO EN PedidoEItemsPedidoDAO para alivianar la consulta a la db
    @Query("SELECT * FROM ItemsPedido WHERE id_pedido = :id_pedido")
    List<ItemsPedido> buscarItemsDeUnPedido(Integer id_pedido);
}
