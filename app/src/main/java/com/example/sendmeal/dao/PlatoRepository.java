package com.example.sendmeal.dao;
import android.os.Message;
import android.util.Log;
import com.example.sendmeal.HomeActivity;
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

    public static final int _ALTA_PLATO = 1;
    public static final int _UPDATE_PLATO = 2;
    public static final int _BORRADO_PLATO = 3;
    public static final int _CONSULTA_PLATO = 4;
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
                .baseUrl(HomeActivity._SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP_2","INSTANCIA CREADA");
        this.platoRest = this.rf.create(PlatoRest.class);
    }


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
                    m.arg1 = _CONSULTA_PLATO;
                    h.sendMessage(m);
                }
            }
            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {

            }
        });
    }

    public void actualizarPlato(final Plato o, final Handler h){
        Call<Plato> llamada = this.platoRest.actualizar(o.getId(),o);
        llamada.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                if(response.isSuccessful()){
                    // NO ESTA FUNCIONANDO ESTE remove( <Plato> )
                    listaPlatos.remove(o);
                    listaPlatos.add(response.body());
                    Message m = new Message();
                    m.arg1 = _UPDATE_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());

            }
        });
    }

    public void crearPlato(Plato p, final Handler h){
        Call<Plato> llamada = this.platoRest.crear(p);
        llamada.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {


                if(response.isSuccessful()){
                    listaPlatos.add(response.body());
                    Message m = new Message();
                    m.arg1 = _ALTA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void borrarPlato(final Plato p, final Handler h){
        Call<Void> llamada = this.platoRest.borrar(p.getId());
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()){
                    // NO ESTA FUNCIONANDO ESTE remove( <Plato> )
                    listaPlatos.remove(p);
                    Message m = new Message();
                    m.arg1 = _BORRADO_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PLATO ;
                h.sendMessage(m);
            }
        });
    }

    public List<Plato> getListaPlatos(){return this.listaPlatos; }
}