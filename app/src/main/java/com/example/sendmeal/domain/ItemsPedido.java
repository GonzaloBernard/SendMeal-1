package com.example.sendmeal.domain;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "itemsPedido")
public class ItemsPedido {

    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
   // @ColumnInfo(index = true, name = COLUMN_ID)
    private Integer id;

    @ForeignKey(entity = Pedido.class, parentColumns = "id", childColumns = "id")
    //@ColumnInfo(name = "pedido")
    private Pedido pedido;

   // @ColumnInfo(name = "plato")//aca plato no es una entidad en la BD, entonces me parece que hay que poner un int id que es el del plato
    private Plato plato;

    //@ColumnInfo(name = "cantidad")
    private Integer cantidad;

    //@ColumnInfo(name = "precio")
    private Double precio;

    public ItemsPedido(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
}
