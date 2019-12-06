package com.example.sendmeal.domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PedidoYTodosSusItems {

    @Embedded
    public Pedido pedido;

    @Relation(parentColumn = "id", entityColumn = "id_pedido",entity = ItemsPedido.class)
    private List<ItemsPedido> items;

    public PedidoYTodosSusItems(){}
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<ItemsPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemsPedido> items) {
        this.items = items;
    }
}
