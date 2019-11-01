package com.example.sendmeal.domain;

public enum EstadoPedido {
    PENDIENTE ("pendiente"), ENVIADO ("enviado"), ACEPTADO ("aceptado"),
    RECHAZADO ("rechazado"), EN_PREPARACION ("en_reparacion"), EN_ENVIO ("en_envio"),
    ENTREGADO ("entregado"), CANCELADO ("cancelado");

/*
    1. PENDIENTE: el pedido fue creado, pero no fue enviado al servidor por lo que aún está
    almacenado localmente
    2. ENVIADO: el pedido fue enviado para que un sea aceptado.
    3. ACEPTADO: el pedido fue aceptado por un administrador del sistema y será derivado a
    un repartidor
    4. RECHAZADO: el pedido fue rechazado por un administrador del sistema y no ser
    realizará
    5. EN_PREPARACION: el repartidor aceptó su pedido comenzó a trabajar en el.
    6. EN_ENVIO: el repartidor tiene todos los ítems pedidos y está en envio.
    7. ENTREGADO: el pedido ha sido entregado.
    8. CANCELADO: Cuando el pedido está en los estados 1 o 2 se puede cancelar.
*/

    private final String text;

    EstadoPedido(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
