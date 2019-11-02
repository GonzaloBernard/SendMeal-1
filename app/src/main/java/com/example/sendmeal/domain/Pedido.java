package com.example.sendmeal.domain;



import android.provider.BaseColumns;


import androidx.room.Entity;
import androidx.room.PrimaryKey;



import java.util.Date;
import java.util.List;

@Entity (tableName = "pedido")
public class Pedido {

    //ATRIBUTOS
        @PrimaryKey(autoGenerate = true)
        private Integer id;

        private Date fecha;
        private EstadoPedido estado;
        private Double latitud;
        private Double longitud;



    //METODOS
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

}
