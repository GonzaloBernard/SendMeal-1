package com.example.sendmeal;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.sendmeal.domain.Plato;
import java.util.ArrayList;
import java.util.List;

public class ListaItems extends AppCompatActivity {
    private final BroadcastReceiver br = new MyReceiver();
    private static final int REQUEST_CODE_EDITAR_PLATO = 2;
    public static final String CHANNEL_ID="10001";
    private RecyclerView.Adapter mAdapter;
    private static List<Plato> _PLATOS = new ArrayList<>();


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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        // PLATOS HARDCODEADOS EN CASO DE NO CREAR NINGUN PLATO MANUALMENTE
        if(_PLATOS.isEmpty()) {
            Plato plato1 = new Plato();
            Plato plato2 = new Plato();
            Plato plato3 = new Plato();
            Plato plato4 = new Plato();

            plato1.setId(56);
            plato1.setTitulo("Hamburguesa completa");
            plato1.setDescripcion("Hamburguesa con queso, lechuga, huevo y tomate");
            plato1.setPrecio(150d);
            plato1.setCalorias(1500);
            plato1.setImagen(R.drawable.hamburguesa);
            plato1.setEnOferta(false);

            plato2.setId(2);
            plato2.setTitulo("Pizza napolitana");
            plato2.setDescripcion("Muzzarela, tomate y oregano");
            plato2.setPrecio(150d);
            plato2.setCalorias(1200);
            plato2.setImagen(R.drawable.pizza);
            plato2.setEnOferta(false);

            plato3.setId(3);
            plato3.setTitulo("Triples de miga");
            plato3.setDescripcion("Sandwich de miga con jamos queso lechuga y tomate");
            plato3.setPrecio(100d);
            plato3.setCalorias(600);
            plato3.setImagen(R.drawable.sandwich);
            plato3.setEnOferta(false);

            plato4.setId(4);
            plato4.setTitulo("Lomo gratinado");
            plato4.setDescripcion("Sandwich de lomo con queso derretido");
            plato4.setPrecio(200d);
            plato4.setCalorias(1300);
            plato4.setImagen(R.drawable.lomito);
            plato4.setEnOferta(false);

            //LISTA DE PLATOS
            _PLATOS.add(plato1);
            _PLATOS.add(plato2);
            _PLATOS.add(plato3);
            _PLATOS.add(plato4);
        }
        ////////////////////////////////////////////////////////////////////////
        //CREACION DEL CANAL DE NOTIFICACIONES
        this.createNotificationChannel();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_EN_OFERTA);
        registerReceiver(br, filtro);
        //LocalBroadcastManager.getInstance(this).registerReceiver(br,filtro);

        // RECYCLER VIEW
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.listaItemsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PlatoAdapter( _PLATOS );
        mRecyclerView.setAdapter(mAdapter);


    }

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
