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

public class PlatoRepository {

    public static String _SERVER = "http://10.0.2.2:3000/";
    public static final int _ALTA_PLATO = 1;
    public static final int _UPDATE_PLATO = 2;
    public static final int _BORRADO_PLATO = 3;
    public static final int CONSULTA_PLATO = 4;
    public static final int _ERROR_PLATO = 9;
    private List<Plato> listaPlatos;

    private static PlatoRepository _INSTANCE;
    private PlatoRepository(){}
    public static PlatoRepository getInstance(){
        if(_INSTANCE==null){
            _INSTANCE = new PlatoRepository();
            _INSTANCE.configurarRetrofit();
            _INSTANCE.listaPlatos = new ArrayList<>();
        }
        return _INSTANCE;
    }

    //CREACION Y CONFIGURACION DE RETROFIT
    private Retrofit rf;
    private PlatoRest platoRest;
    private void configurarRetrofit(){
        this.rf = new Retrofit.Builder()
                .baseUrl(_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP_2","INSTANCIA CREADA");
        this.platoRest = this.rf.create(PlatoRest.class);
    }

    //HANDLER PARA MANEJAR LA COMUNICACION CON EL API REST
    public void listarPlatos(final Handler h){
        //SE GENERA UN HTTP REQUEST
        Call<List<Plato>> call = this.platoRest.getPlatos();
        call.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()) {
                    //SE RECUPERAN LOS PLATOS DE LA API
                    List<Plato> platos = response.body();
                    listaPlatos.clear();
                    listaPlatos.addAll(response.body());
                    // UNA VEZ RECUPERADOS LOS PLATOS DE LA API SE CREA Y ENVIA UN MENSAJE PARA
                    // QUE EL HANDLER DE ListaItems ACTUALICE SU LISTA DE PLATOS
                    Message m = new Message();
                    m.arg1 = CONSULTA_PLATO;
                    h.sendMessage(m);
                }
            }
            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {

            }
        });
    }
    public List<Plato> getListaPlatos(){return this.listaPlatos; }

}