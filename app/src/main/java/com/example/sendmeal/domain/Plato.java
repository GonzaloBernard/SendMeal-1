package com.example.sendmeal.domain;

import android.widget.ImageView;

public class Plato {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Integer calorías;
    private Integer imagen;


    public Plato() {
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getCalorías() {
        return calorías;
    }

    public void setCalorías(Integer calorías) {
        this.calorías = calorías;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTítulo() {
        return titulo;
    }

    public void setTítulo(String título) {
        this.titulo = título;
    }

    @Override
    public String toString() {
        return "Plato{" +
                "titulo='" + titulo + '\'' +
                '}';
    }
}