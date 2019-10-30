package com.example.sendmeal.domain;



import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

@Entity (tableName = "pedido")
public class Pedido {

    public static final String COLUMN_ID = BaseColumns._ID;

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true, name = COLUMN_ID)
        private Integer id;

        @ColumnInfo(name = "fecha")
        private Date fecha;

        @ColumnInfo(name = "estado")
        private EstadoPedido estado;

        @ColumnInfo(name = "latitud")
        private Double latitud;

        @ColumnInfo(name = "longitud")
        private Double longitud;


        private List<ItemsPedido> items;

    public Pedido() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public List<ItemsPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemsPedido> items) {
        this.items = items;
    }
}
