package com.example.sendmeal.domain;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.List;

@Entity (tableName = "pedido")
public class Pedido implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Date fecha;
    private EstadoPedido estado;
    private Double latitud;
    private Double longitud;
    @Ignore
    private List<ItemsPedido> itemsPedido;

    public Pedido() {}

    public Pedido(Parcel in){
        readFromParcel(in);
    }
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

    public List<ItemsPedido> getItemsPedido() {
        return itemsPedido;
    }

    public void setItemsPedido(List<ItemsPedido> itemsPedido) {
        this.itemsPedido = itemsPedido;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(fecha.getTime());
        dest.writeString(estado.toString());
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeList(itemsPedido);
        }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.fecha = new Date(in.readLong());
        this.estado = EstadoPedido.valueOf(in.readString());
        this.latitud = in.readDouble();
        this.longitud = in.readDouble();
        in.readList(this.itemsPedido, this.getClass().getClassLoader());
    }

    public static final Parcelable.Creator<Pedido> CREATOR = new Parcelable.Creator<Pedido>() {
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };
}
