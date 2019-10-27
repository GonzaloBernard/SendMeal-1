package com.example.sendmeal.dao;
import android.os.Message;
import android.util.Log;
import com.example.sendmeal.dao.rest.PlatoRest;
import com.example.sendmeal.domain.Plato;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.os.Handler;
import android.widget.Toast;


public class PlatoRepository {

    public static String _SERVER = "http://10.0.2.2:3000/";
    public static final int _ALTA_PLATO =1;
    public static final int _UPDATE_PLATO =2;
    public static final int _BORRADO_PLATO =3;
    public static final int _CONSULTA_PLATO =4;
    public static final int _ERROR_PLATO =9;

    private static PlatoRepository _INSTANCE;
    private Retrofit rf;
    private PlatoRest platoRest;
    private PlatoRepository(){}
    private List<Plato> listaPlatos;

    public static PlatoRepository getInstance(){
        if(_INSTANCE==null){
            _INSTANCE = new PlatoRepository();
            _INSTANCE.configurarRetrofit();
            _INSTANCE.listaPlatos = new ArrayList<>();
        }
        return _INSTANCE;
    }

    private void configurarRetrofit(){
        this.rf = new Retrofit.Builder()
                .baseUrl(_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP_2","INSTANCIA CREADA");

        this.platoRest = this.rf.create(PlatoRest.class);
    }

    public List<Plato> getPlatos(){
        Call<List<Plato>> call = this.platoRest.getPlatos();
        call.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                List<Plato> platos = response.body();

                //ESTA LINEA ES SOLAMENTE PARA HACER UN BREAKPOINT Y VER SI RESPONSE TRAJO PLATOS DEL API REST
                Integer breakpoint = null;
            }
            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {

            }
        });
        return null;
    }

}