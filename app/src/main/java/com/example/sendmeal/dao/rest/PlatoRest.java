package com.example.sendmeal.dao.rest;
import com.example.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;

public interface PlatoRest {
    @GET("platos")
    Call<List<Plato>> getPlatos();

    @PUT("platos/{id}")
    Call<Plato> actualizar(@Path("id") Integer id, @Body Plato plato);

    @POST("platos/")
    Call<Plato> crear(@Body Plato plato);

    @DELETE("platos/{id}")
    Call<Void> borrar(@Path("id") Integer id);

    
}
