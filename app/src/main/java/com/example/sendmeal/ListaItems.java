package com.example.sendmeal;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.Plato;
import java.util.ArrayList;
import java.util.List;

public class ListaItems extends AppCompatActivity {
    private final BroadcastReceiver br = new MyReceiver();
    private static final int REQUEST_CODE_EDITAR_PLATO = 2;
    public static final String CHANNEL_ID="10001";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private static List<Plato> _PLATOS = new ArrayList<>();
    private List<Plato>  listaDataSet;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if( resultCode== Activity.RESULT_OK){
            if(requestCode==REQUEST_CODE_EDITAR_PLATO){
                // OBTENIENDO LOS DATOS DEL PLATO MODIFICADO
                try {
                    Bundle extras = data.getExtras();
                    Plato plato = (Plato) data.getParcelableExtra(HomeActivity.PLATO_INDIVIDUAL_KEY);
                    // RECUPERANDO EL PLATO VIEJO POR SU ID
                    Plato platoViejo = getPlatoById(plato.getId());
                    //SE SACA DE LA LISTA PARA ACTUALIZARLO
                    _PLATOS.remove(platoViejo);
                    //SE VUELVE A CARGAR EL LA LISTA
                    _PLATOS.add(plato);
                    Toast.makeText(this, R.string.listaItemsPlatoEditado, Toast.LENGTH_LONG).show();
                    //SE LE DICE AL ADAPTER QUE ACTUALICE LA DATA
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_volver, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_items);
        mRecyclerView = (RecyclerView) findViewById(R.id.listaItemsRecyclerView);
        //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarListaItems);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tituloToolbarListarItems);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            ArrayList<Plato> platosNuevos = getIntent().getParcelableArrayListExtra(HomeActivity.PLATOS_LISTA_KEY);
            _PLATOS.addAll(platosNuevos);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ////////////////////////////////////////////////////////////////////////
        // PLATOS HARDCODEADOS EN CASO DE NO CREAR NINGUN PLATO MANUALMENTE
        if(_PLATOS.isEmpty()) {
            Plato plato1 = new Plato();
            plato1.setId(56);
            plato1.setTitulo("Hamburguesa completa");
            plato1.setDescripcion("Hamburguesa con queso, lechuga, huevo y tomate");
            plato1.setPrecio(150d);
            plato1.setCalorias(1500);
            plato1.setImagen(R.drawable.hamburguesa);
            plato1.setEnOferta(false);
            //LISTA DE PLATOS
            _PLATOS.add(plato1);
        }

        //CREACION DEL CANAL DE NOTIFICACIONES
        this.createNotificationChannel();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_EN_OFERTA);
        registerReceiver(br, filtro);
        //LocalBroadcastManager.getInstance(this).registerReceiver(br,filtro);

        // SE PIDEN LOS PLATOS A PlatoRepository
        PlatoRepository.getInstance().listarPlatos(miHandler);
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            listaDataSet = PlatoRepository.getInstance().getListaPlatos();

            switch (msg.arg1 ){
                case PlatoRepository.CONSULTA_PLATO:
                    // RECYCLER VIEW
                    mRecyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ListaItems.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new PlatoAdapter( listaDataSet );
                    mRecyclerView.setAdapter(mAdapter);
                    break;
                default:
                    Toast.makeText(ListaItems.this, "HANDLER DEFAULT CASE", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    public void onDestroy() {
        unregisterReceiver(br);
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
        super.onDestroy();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CANAL SEND MEAL";
            String description = "Este canal esta creado para configurar las notificaciones de Send Meal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    private Plato getPlatoById(Integer id){
        for(Plato plato: _PLATOS){
            if(plato.getId().equals(id)){
                return plato;
            }
        }
        return null;
    }

}
