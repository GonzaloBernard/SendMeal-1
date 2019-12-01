package com.example.sendmeal.dao;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.room.Room;
import com.example.sendmeal.HomeActivity;
import com.example.sendmeal.dao.database.AppDataBase;
import com.example.sendmeal.dao.database.PedidoDao;
import com.example.sendmeal.dao.rest.PedidoRest;
import com.example.sendmeal.domain.Pedido;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PedidoRepository {

    private static PedidoRepository _PEDIDO_REPOSITORY = null;
    public static final int _CONSULTA_PEDIDO=4;
    public static final int _ALTA_PEDIDO_REST = 20;
    public static final int _ERROR_PEDIDO_REST = 29;
    private PedidoDao pedidoDao;
    private List<Pedido> listaPedidosSQLite;
    private List<Pedido> listaPedidosREST;

    private PedidoRepository(Context ctx){
        AppDataBase db = Room.databaseBuilder(ctx,
                AppDataBase.class, "sendmeal-db")
                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
                .build();
        pedidoDao = db.pedidoDao();
    }
    public static PedidoRepository getInstance(Context ctx){
        if(_PEDIDO_REPOSITORY ==null){
            _PEDIDO_REPOSITORY = new PedidoRepository(ctx);
            _PEDIDO_REPOSITORY.configurarRetrofit();
            _PEDIDO_REPOSITORY.listaPedidosSQLite = new ArrayList<>();
            _PEDIDO_REPOSITORY.listaPedidosREST = new ArrayList<>();
        }
        return _PEDIDO_REPOSITORY;
    }

    // ACCIONES SOBRE SQLite
    public long crearPedidoSQL(Pedido p){
        return pedidoDao.crearPedido(p);
    }
    public void borrarPedidoSQL(Pedido p){
        pedidoDao.borrarPedido(p);
    }
    public void actualizarPedidoSQL(Pedido p){
        pedidoDao.actualizarPedido(p);
    }
    public Pedido buscarPedidoPorIDSQL(Integer id){
        return pedidoDao.buscarPedidoPorID(id);
    }
    public List<Pedido> buscarPedidosSQL(){
        return pedidoDao.buscarPedidos();
    }

    //CREACION Y CONFIGURACION DE RETROFIT
    private Retrofit rf;
    private PedidoRest pedidoRest;

    void configurarRetrofit(){
        this.rf = new Retrofit.Builder()
                .baseUrl(HomeActivity.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("APP_2","INSTANCIA CREADA");
        this.pedidoRest = this.rf.create(PedidoRest.class);
    }
    // ACCIONES SORE API REST

    public void crearPedidoREST(Pedido p, final Handler h){
        Call<Pedido> llamada = this.pedidoRest.crear(p);
        llamada.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    Pedido pedidoGuardado =response.body();
                    listaPedidosREST.add(pedidoGuardado);
                    Message m = new Message();
                    m.arg1 = _ALTA_PEDIDO_REST;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.d("APP_2","ERROR "+t.getMessage());
                Message m = new Message();
                m.arg1 = _ERROR_PEDIDO_REST;
                h.sendMessage(m);
            }
        });
    }

    public void listarPedidos(final Handler h){
        //SE GENERA UN HTTP REQUEST
        Call<List<Pedido>> call = this.pedidoRest.getPedidos();
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.isSuccessful()) {
                    //SE RECUPERAN LOS PEDIDOS DE LA API
                    List<Pedido> pedidos = response.body();
                    listaPedidosREST.clear();
                    listaPedidosREST.addAll(response.body());
                    // UNA VEZ RECUPERADOS LOS PEDIDOS DE LA API SE CREA Y ENVIA UN MENSAJE PARA
                    // QUE EL HANDLER DE ListaItems ACTUALICE SU LISTA DE PEDIDOS
                    Message m = new Message();
                    m.arg1 = _CONSULTA_PEDIDO;
                    m.obj = pedidos;
                    h.sendMessage(m);
                }
            }
            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {

            }
        });
    }

    public List<Pedido> getListaPedidos(){
        return listaPedidosREST;}
}