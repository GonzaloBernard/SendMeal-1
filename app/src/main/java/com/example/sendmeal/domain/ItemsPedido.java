package com.example.sendmeal.domain;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "itemsPedido")
public class ItemsPedido implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ForeignKey(entity = Pedido.class, parentColumns = "id", childColumns = "id_pedido", onUpdate = ForeignKey.CASCADE)
    private Integer id_pedido;
    private Plato plato;
    private Integer cantidad;
    private Double precio;

    public ItemsPedido(){}

    public ItemsPedido (Parcel in) {
        readFromParcel(in);
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(id_pedido);
        dest.writeParcelable(plato, flags);
        dest.writeInt(cantidad);
        dest.writeDouble(precio);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.id_pedido = in.readInt();
        this.plato = in.readParcelable(Plato.class.getClassLoader());
        this.cantidad = in.readInt();
        this.precio = in.readDouble();
    }

    public static final Parcelable.Creator<ItemsPedido> CREATOR = new Parcelable.Creator<ItemsPedido>() {
        public ItemsPedido createFromParcel(Parcel in) {
            return new ItemsPedido(in);
        }

        public ItemsPedido[] newArray(int size) {
            return new ItemsPedido[size];
        }
    };

}
