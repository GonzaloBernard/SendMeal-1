package com.example.sendmeal;

import java.util.Objects;

public class Usuario {
    private Integer id;
    private String nombre;
    private String mail;
    private String clave;
    private Boolean notificarMail;
    private Double credito;
    private TipoCuenta tipoCuenta;



    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getNotificarMail() {
        return notificarMail;
    }

    public void setNotificarMail(Boolean notificarMail) {
        this.notificarMail = notificarMail;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", clave='" + clave + '\'' +
                ", notificarMail=" + notificarMail +
                ", credito=" + credito +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getId(), usuario.getId()) &&
                Objects.equals(getNombre(), usuario.getNombre()) &&
                Objects.equals(getMail(), usuario.getMail()) &&
                Objects.equals(getClave(), usuario.getClave()) &&
                Objects.equals(getNotificarMail(), usuario.getNotificarMail()) &&
                Objects.equals(getCredito(), usuario.getCredito());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getMail(), getClave(), getNotificarMail(), getCredito());
    }
}
