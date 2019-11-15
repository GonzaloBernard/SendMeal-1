package com.example.sendmeal.domain;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity (tableName = "itemsPedido")
public class ItemsPedido {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ForeignKey(entity = Pedido.class, parentColumns = "id", childColumns = "id_pedido", onUpdate = ForeignKey.CASCADE)
    private Integer id_pedido;
    private Plato plato;
    private Integer cantidad;
    private Double precio;

    public ItemsPedido(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return cantidad + " x " + plato.getTitulo();
    }
}
