package com.example.sendmeal;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.sendmeal.domain.Plato;

import java.util.Calendar;
import java.util.List;

public class AltaPedido extends AppCompatActivity {

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

        //DECLARACION DE VIEWS
        Button buttonCreaPedido = (Button) findViewById(R.id.buttonCrearPedido);
        Button buttonEnviarPedido = (Button) findViewById(R.id.buttonEnviarPedido);

        buttonCreaPedido.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // SE CREA EL PEDIDO (HARDCODEADO)
                    Pedido pedido = new Pedido();
                    pedido.setFecha(Calendar.getInstance().getTime());
                    pedido.setEstado(EstadoPedido.PENDIENTE);
                    pedido.setLatitud(null);
                    pedido.setLongitud(null);

                    // SE PIDE UNA INSTANCIA DEL REPO
                    PedidoRepository pedidoRepository = PedidoRepository.getInstance(AltaPedido.this);
                    // SE GUARDA EL PEDIDO
                    pedidoRepository.crearPedido(pedido);
                    // SE BUSCAN TODOS LOS PEDIDO PARA VER SI SE GUARDO CORRECTAMENTE
                    List<Pedido> lista = pedidoRepository.buscarPedidos();
                    Pedido pedidoAux = lista.get(lista.size() - 1);
                    Toast.makeText(AltaPedido.this,"Pedido "+ pedidoAux.getId() +" creado con éxito",Toast.LENGTH_LONG).show();

                    // SE CREA UN PLATO

                    List<Plato>listaPlatos = PlatoRepository.getInstance().getListaPlatos();


                    // SE CREA UN ItemPedido
                    ItemsPedido itemsPedido1 = new ItemsPedido();
                    itemsPedido1.setCantidad(5);
                    itemsPedido1.setId_pedido(pedidoAux.getId());
                    itemsPedido1.setPlato(listaPlatos.get(0));
                    itemsPedido1.setPrecio(listaPlatos.get(0).getPrecio());

                    ItemsPedido itemsPedido2 = new ItemsPedido();
                    itemsPedido2.setCantidad(1);
                    itemsPedido2.setId_pedido(pedidoAux.getId());
                    itemsPedido2.setPlato(listaPlatos.get(1));
                    itemsPedido2.setPrecio(listaPlatos.get(1).getPrecio());

                    ItemsPedidoRepository itemsPedidoRepository = ItemsPedidoRepository.getInstance(AltaPedido.this);
                    itemsPedidoRepository.crearItemsPedido(itemsPedido1);
                    itemsPedidoRepository.crearItemsPedido(itemsPedido2);

                    List<ItemsPedido> list = itemsPedidoRepository.buscarItemsDeUnPedido(pedidoAux.getId());
                    List<ItemsPedido> listaompleta = itemsPedidoRepository.buscarItemsPedido();
                    Toast.makeText(AltaPedido.this," NADA ",Toast.LENGTH_LONG).show();

                }
                catch (Exception e){
                    Toast.makeText(AltaPedido.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
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

