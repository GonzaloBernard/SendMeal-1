package com.example.sendmeal.domain;

import java.util.Objects;

public class CuentaBancaria {
    private Integer id;
    private String cbu;
    private String alias;

    public CuentaBancaria() {
    }

    public Integer getId() {
        return id;
    }

    public String getCbu() {
        return cbu;
    }

    public String getAlias() {
        return alias;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "id=" + id +
                ", cbu='" + cbu + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentaBancaria that = (CuentaBancaria) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getCbu(), that.getCbu()) &&
                Objects.equals(getAlias(), that.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCbu(), getAlias());
    }
}
