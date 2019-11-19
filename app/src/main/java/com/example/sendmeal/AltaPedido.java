package com.example.sendmeal;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.sendmeal.dao.ItemsPedidoRepository;
import com.example.sendmeal.dao.PedidoRepository;
import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.ItemsPedido;
import com.example.sendmeal.domain.Pedido;
import java.util.Calendar;
import java.util.List;

public class AltaPedido extends AppCompatActivity {
    ListView lvItemsPedido;
    ArrayAdapter<ItemsPedido> adapter;
    List<ItemsPedido> listaItemsPedidoDataset;
    ItemsPedido itemsPedidoSeleccionado;
    Button buttonEnviarPedido;
    Pedido pedidoCreado;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_volver, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_pedido);
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPedido);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tituloToolbarPedido);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // LIST VIEW de ItemsPedido
        lvItemsPedido =(ListView) findViewById(R.id.listViewItemsPedido);
        lvItemsPedido.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        // OBTENER ItemsPedido
        listaItemsPedidoDataset = ListaItems.getListaItemsPedido();

        adapter = new ArrayAdapter<>(AltaPedido.this,android.R.layout.simple_list_item_single_choice, listaItemsPedidoDataset);
        lvItemsPedido.setAdapter(adapter);

        buttonEnviarPedido = (Button) findViewById(R.id.buttonEnviarPedido);
        final Button buttonCrearPedido = (Button) findViewById(R.id.buttonCrearPedido);

        //BUTTON enviar pedido
        if(pedidoCreado!=null){
            buttonEnviarPedido.setEnabled(true);
            buttonCrearPedido.setEnabled(false);
        }
        else {
            buttonEnviarPedido.setEnabled(false);
            buttonCrearPedido.setEnabled(true);
        }
        buttonEnviarPedido.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                buttonEnviarPedido.setEnabled(false);
                pedidoCreado.setEstado(EstadoPedido.ENVIADO);
                pedidoCreado.setItemsPedido(listaItemsPedidoDataset);
                // SE PIDE UNA INSTANCIA DEL REPO
                PedidoRepository pedidoRepository = PedidoRepository.getInstance(AltaPedido.this);
                // SE ENVIA AL API REST
                pedidoRepository.crearPedidoREST(pedidoCreado, miHandler);
                pedidoCreado=null;
                listaItemsPedidoDataset.clear();
                ListaItems.clearListaItems();
                Intent intentResultado = new Intent();
                setResult(Activity.RESULT_OK, intentResultado);
                finish();
            }
        });

        //BOTONES CREAR PEDIDO
        buttonCrearPedido.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buttonCrearPedido.setEnabled(false);
                    // SE CREA EL PEDIDO
                    Pedido pedido = new Pedido();
                    pedido.setFecha(Calendar.getInstance().getTime());
                    pedido.setEstado(EstadoPedido.PENDIENTE);
                    pedido.setLatitud(50005462.456);
                    pedido.setLongitud(85465.660);
                    pedido.setFcmToken(getTokenFromPrefs());
                    CrearPedidoSQL crearPedidoSQL = new CrearPedidoSQL();
                    crearPedidoSQL.execute(pedido);
                }
                catch (Exception e){
                    Toast.makeText(AltaPedido.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.arg1 ) {
                case PedidoRepository._ALTA_PEDIDO_REST:
                    Toast.makeText(AltaPedido.this,"Pedido enviado al API REST",Toast.LENGTH_LONG).show();
                    break;
                case PedidoRepository._ERROR_PEDIDO_REST:
                    Toast.makeText(AltaPedido.this,"El pedido no pudo procesarse",Toast.LENGTH_LONG).show();
                    break;
                default:break;
            }
        }
    };

    class CrearPedidoSQL extends AsyncTask<Pedido, Void, Void> {

        @Override
        protected Void doInBackground(Pedido... pedido) {
            // SE PIDE UNA INSTANCIA DEL REPO
            PedidoRepository pedidoRepository = PedidoRepository.getInstance(AltaPedido.this);
            // SE GUARDA EL PEDIDO
            Integer pedidoCreadoID = (int) pedidoRepository.crearPedidoSQL(pedido[0]);

            pedidoCreado = pedidoRepository.buscarPedidoPorIDSQL( pedidoCreadoID);
            // Se guardan todos los ItemsPedido
            ItemsPedidoRepository itemsPedidoRepository = ItemsPedidoRepository.getInstance(AltaPedido.this);
            for(ItemsPedido itemsPedido:listaItemsPedidoDataset){
                itemsPedido.setId_pedido(pedidoCreadoID);
                itemsPedidoRepository.crearItemsPedido(itemsPedido);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            buttonEnviarPedido.setEnabled(true);
        }
    }
    private String getTokenFromPrefs(){
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }

}

