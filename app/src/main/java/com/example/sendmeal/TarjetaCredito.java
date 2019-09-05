package com.example.sendmeal;

import java.util.Objects;

public class TarjetaCredito {
    private Integer id;
    private String numero;
    private Integer digitoVerificacion;
    private String vencimiento;

    public TarjetaCredito() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getDigitoVerificacion() {
        return digitoVerificacion;
    }

    public void setDigitoVerificacion(Integer digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    @Override
    public String toString() {
        return "TarjetaCredito{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", digitoVerificacion=" + digitoVerificacion +
                ", vencimiento='" + vencimiento + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarjetaCredito that = (TarjetaCredito) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getNumero(), that.getNumero()) &&
                Objects.equals(getDigitoVerificacion(), that.getDigitoVerificacion()) &&
                Objects.equals(getVencimiento(), that.getVencimiento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumero(), getDigitoVerificacion(), getVencimiento());
    }
}
