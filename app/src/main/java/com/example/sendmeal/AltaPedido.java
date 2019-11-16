package com.example.sendmeal;


import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sendmeal.dao.ItemsPedidoRepository;
import com.example.sendmeal.dao.PedidoRepository;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.ItemsPedido;
import com.example.sendmeal.domain.Pedido;
import com.example.sendmeal.domain.PedidoYTodosSusItems;
import com.example.sendmeal.domain.Plato;

import java.util.Calendar;
import java.util.List;

public class AltaPedido extends AppCompatActivity {
    ListView lvItemsPedido;
    ArrayAdapter<ItemsPedido> adapter;
    List<ItemsPedido> listaItemsPedidoDataset;
    ItemsPedido itemsPedidoSeleccionado;
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

        final Button buttonEnviarPedido = (Button) findViewById(R.id.buttonEnviarPedido);
        final Button buttonCreaPedido = (Button) findViewById(R.id.buttonCrearPedido);

        //BUTTON enviar pedido
        buttonEnviarPedido.setEnabled(false);

        buttonEnviarPedido.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                buttonEnviarPedido.setEnabled(false);

                // SE PIDE UNA INSTANCIA DEL REPO
                PedidoRepository pedidoRepository = PedidoRepository.getInstance(AltaPedido.this);
                Pedido pedidoRecuperado = pedidoRepository.buscarPedidoPorID(
                        pedidoRepository.buscarPedidos().get(pedidoRepository.buscarPedidos().size()-1 ).getId());
                pedidoRecuperado.setEstado(EstadoPedido.ENVIADO);
                // ENVIAR AL API REST
                // SE PIDEN LOS PLATOS A PlatoRepository
                pedidoRepository.crearPedido(pedidoRecuperado, miHandler);

                //////////////////////////////////////////////////
                listaItemsPedidoDataset.clear();
                ListaItems.clearListaItems();
                Intent intentResultado = new Intent();
                setResult(Activity.RESULT_OK, intentResultado);
                finish();
                Toast.makeText(AltaPedido.this, "El pedido ha sido creado", Toast.LENGTH_LONG).show();
            }
        });

        //BOTONES CREAR PEDIDO
        buttonCreaPedido.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // SE CREA EL PEDIDO
                    Pedido pedido = new Pedido();
                    pedido.setFecha(Calendar.getInstance().getTime());
                    pedido.setEstado(EstadoPedido.PENDIENTE);
                    pedido.setLatitud(null);
                    pedido.setLongitud(null);

                    // SE PIDE UNA INSTANCIA DEL REPO
                    PedidoRepository pedidoRepository = PedidoRepository.getInstance(AltaPedido.this);
                    // SE GUARDA EL PEDIDO
                    pedidoRepository.crearPedido(pedido);
                    // LO RECUPERO PARA OBTENER EL id ???????????? BUSCAR UNA MEJOR FORMA
                    Pedido pedidoRecuperado = pedidoRepository.buscarPedidoPorID(
                            pedidoRepository.buscarPedidos().get(pedidoRepository.buscarPedidos().size()-1 ).getId());
                    ItemsPedidoRepository itemsPedidoRepository = ItemsPedidoRepository.getInstance(AltaPedido.this);
                    for(ItemsPedido itemsPedido:listaItemsPedidoDataset){
                        itemsPedido.setId_pedido(pedidoRecuperado.getId());
                        itemsPedidoRepository.crearItemsPedido(itemsPedido);
                    }

                    buttonCreaPedido.setEnabled(false);
                    buttonEnviarPedido.setEnabled(true);

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
                    Toast.makeText(AltaPedido.this,"Pedido no pudo procesarse",Toast.LENGTH_LONG).show();
                    break;
                default:break;
            }
        }
    };
    /*
    class GuardarPedido extends AsyncTask<Pedido, Void, Void> {

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            PedidoDao dao = PedidoRepository.getInstance(AltaPedido.this).getAppDataBase().pedidoDao();
            if(pedidos[0].getId() != null && pedidos[0].getId() >0) {
                dao.actualizar(pedidos[0]);
            }else {
                dao.insert(pedidos[0]);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    */
}

