package com.example.sendmeal;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sendmeal.dao.database.DataBaseClient;
import com.example.sendmeal.dao.database.PedidoDao;
import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.Pedido;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                Date date = Calendar.getInstance().getTime();
                Pedido pedido = new Pedido();
                //pedido.setId(01);
                pedido.setFecha(date);
                pedido.setEstado(EstadoPedido.PENDIENTE);
                pedido.setLatitud(null);
                pedido.setLongitud(null);

                GuardarPedido tareaGuardarPedido = new GuardarPedido();
                tareaGuardarPedido.execute(pedido);


                //PedidoDao dao = DataBaseClient.getInstance(AltaPedido.this).getAppDataBase().pedidoDao();
                //List<Pedido> listaObrasDataset =dao.getAll();
                //Pedido p = listaObrasDataset.get(0);
            }
        });
    }
    class GuardarPedido extends AsyncTask<Pedido, Void, Void> {

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            PedidoDao dao = DataBaseClient.getInstance(AltaPedido.this).getAppDataBase().pedidoDao();
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

}

