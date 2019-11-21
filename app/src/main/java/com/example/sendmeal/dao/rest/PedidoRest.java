package com.example.sendmeal.dao.rest;

import com.example.sendmeal.domain.Pedido;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PedidoRest {
    @GET("pedidos")
    Call<List<Pedido>> getPedidos();

    @PUT("pedidos/{id}")
    Call<Pedido> actualizar(@Path("id") Integer id, @Body Pedido pedido);

    @POST("pedidos/")
    Call<Pedido> crear(@Body Pedido pedido);

    @DELETE("pedidos/{id}")
    Call<Void> borrar(@Path("id") Integer id);
}
