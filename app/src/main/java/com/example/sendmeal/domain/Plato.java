package com.example.sendmeal.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Plato implements Parcelable {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Integer calorias;
    private Integer imagen;
    private Boolean enOferta;

    @Override
    public boolean equals(Object o) {
        Plato plato = (Plato) o;
        if (this.getId() == plato.getId() ) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Plato() {
    }

    public Plato(Parcel in){
        readFromParcel(in);
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public Integer getImagen() {
        return imagen;
    }

    public Boolean getEnOferta() {
        return enOferta;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

    public void setEnOferta(Boolean enOferta) {
        this.enOferta = enOferta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(descripcion);
        dest.writeDouble(precio);
        dest.writeInt(calorias);
        dest.writeInt(imagen);
        dest.writeInt(enOferta ? 1 : 0);
        }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.titulo = in.readString();
        this.descripcion = in.readString();
        this.precio = in.readDouble();
        this.calorias = in.readInt();
        this.imagen = in.readInt();
        this.enOferta = in.readInt() == 1;

    }

    public static final Parcelable.Creator<Plato> CREATOR = new Parcelable.Creator<Plato>() {
        public Plato createFromParcel(Parcel in) {
            return new Plato(in);
        }

        public Plato[] newArray(int size) {
            return new Plato[size];
        }
    };
}