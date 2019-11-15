package com.example.sendmeal.domain;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.List;

@Entity (tableName = "pedido")
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Date fecha;
    private EstadoPedido estado;
    private Double latitud;
    private Double longitud;
    @Ignore
    private List<ItemsPedido> itemsPedido;

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
        return itemsPedido;
    }

    public void setItems(List<ItemsPedido> items) {
        this.itemsPedido = items;
    }

    public void addItem(ItemsPedido itemPedido){
        this.itemsPedido.add(itemPedido);
    }


}
