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
import com.example.sendmeal.domain.ItemsPedido;
import com.example.sendmeal.domain.Plato;
import java.util.ArrayList;
import java.util.List;

public class ListaItems extends AppCompatActivity {
    private final BroadcastReceiver br = new MyReceiver();
    public static final int REQUEST_CODE_EDITAR_PLATO = 2;
    public static final int REQUEST_CODE_BORRAR_PLATO = 3;
    public static final int REQUEST_CODE_ALTA_PEDIDO = 15;
    public static final String CHANNEL_ID="10001";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Plato>  listaDataSet;
    static List<ItemsPedido> listaItemsPedido = new ArrayList<>();

    public static List<ItemsPedido> getListaItemsPedido() {
        return listaItemsPedido;
    }
    public static void addListaItemsPedido(ItemsPedido itemsPedido) {
        listaItemsPedido.add(itemsPedido);
    }
    public static void clearListaItems() {
        listaItemsPedido.clear();
    }

    public static final Integer _KEY_CALL_BUSCAR_PLATO_AC = 6;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if( resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_EDITAR_PLATO:
                    try {
                        Bundle extras = data.getExtras();
                        // SE OBTIENE EL PLATO A MODIFICAR
                        Plato plato = (Plato) data.getParcelableExtra(AbmPlato._PLATO_INDIVIDUAL_KEY);
                        // SE ACTUALIZA EL PLATO EN EL SERVIDOR
                        PlatoRepository.getInstance().actualizarPlato(plato, miHandler);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case REQUEST_CODE_BORRAR_PLATO:
                    try {
                        Bundle extras = data.getExtras();
                        // SE OBTIENE EL PLATO A MODIFICAR
                        Plato plato = (Plato) data.getParcelableExtra(AbmPlato._PLATO_INDIVIDUAL_KEY);
                        // SE BORRA EL PLATO EN EL SERVIDOR
                        PlatoRepository.getInstance().borrarPlato(plato, miHandler);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case REQUEST_CODE_ALTA_PEDIDO:
                    finish();
                    break;
                default: break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_carrito, menu);
        // SI ES EL MENU DEL VENDEDOR NO SE MUESTRA EL CARRITO DE COMPRA
        if( getIntent().getStringExtra(HomeActivity._TIPO_USUARIO).equals(HomeActivity.KEY_VENDEDOR )){
            menu.findItem(R.id.toolbarCarrito).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarCarrito:
                Intent i1 = new Intent(ListaItems.this, AltaPedido.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(i1, REQUEST_CODE_ALTA_PEDIDO);
                return true;

            case R.id.toolbarBuscarItems:
                Intent i5 = new Intent(ListaItems.this, BuscarPlatos.class);
                i5.putExtra(HomeActivity.getTipoUsuario(), getIntent().getStringExtra(HomeActivity.getTipoUsuario()));
                startActivity(i5);
                return true;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                clearListaItems();
                onBackPressed();
                return true;
            default:
                Toast.makeText(this,". . . . ",Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
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

        // RECYCLER VIEW
        mRecyclerView = (RecyclerView) findViewById(R.id.listaItemsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ListaItems.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //CREACION DEL CANAL DE NOTIFICACIONES
        this.createNotificationChannel();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_EN_OFERTA);
        registerReceiver(br, filtro);


        // Se determina si la lista debe estar filtrada o completa
        if (getIntent().getStringExtra("FILTRO") != null) {
            String titulo = getIntent().getStringExtra("titulo");
            Double precioMin = getIntent().getDoubleExtra("precioMin", 0);
            Double precioMax = getIntent().getDoubleExtra("precioMax", 0);
            PlatoRepository.getInstance().listarPlatosFiltrados(miHandler, titulo, precioMin, precioMax);
        } else {
            // SE PIDEN LOS PLATOS A PlatoRepository
            PlatoRepository.getInstance().listarPlatos(miHandler);
        }
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {

            //Lista de platos traidos del json server
            if (getIntent().getStringExtra("FILTRO") != null) {
                listaDataSet = PlatoRepository.getInstance().getListaPlatosFiltrados();
            }else{
                listaDataSet = PlatoRepository.getInstance().getListaPlatos();
            }

            switch (msg.arg1 ) {
                case PlatoRepository._CONSULTA_PLATO:
                case PlatoRepository._UPDATE_PLATO:
                case PlatoRepository._BORRADO_PLATO:
                    // ACTUALIZAR RECYCLER VIEW
                    mAdapter = new PlatoAdapter( listaDataSet , getIntent().getStringExtra(HomeActivity._TIPO_USUARIO));
                    mRecyclerView.setAdapter(mAdapter);
                default:break;
            }
        }
    };


    @Override
    public void onDestroy() {
        unregisterReceiver(br);
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

}
