package com.example.sendmeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sendmeal.domain.Plato;

import java.util.ArrayList;

public class ListaItems extends AppCompatActivity {

    private Toolbar toolbar;

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
        toolbar = (Toolbar) findViewById(R.id.toolbarListaItems);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // PLATOS HARDCODEADOS
        Plato plato1 = new Plato();
        Plato plato2 = new Plato();
        Plato plato3 = new Plato();
        Plato plato4 = new Plato();
        plato1.setId(1);
        plato1.setTítulo("Hamburguesa completa");
        plato1.setDescripcion("Hamburguesa con queso, lechuga, huevo y tomate");
        plato1.setPrecio(150d);
        plato1.setCalorías(1500);
        plato2.setId(2);
        plato2.setTítulo("Pizza napolitana");
        plato2.setDescripcion("Muzzarela, tomate y oregano");
        plato2.setPrecio(150d);
        plato2.setCalorías(1200);
        plato3.setId(3);
        plato3.setTítulo("Triples de miga");
        plato3.setDescripcion("Sandwich de miga con jamos queso lechuga y tomate");
        plato3.setPrecio(100d);
        plato3.setCalorías(600);
        plato4.setId(4);
        plato4.setTítulo("Lomo gratinado");
        plato4.setDescripcion("Sandwich de lomo con queso derretido");
        plato4.setPrecio(200d);
        plato4.setCalorías(1300);


        //LISTA DE PLATOS
        ArrayList<Plato> listaPlatos = new ArrayList<>();
        listaPlatos.add(plato1);
        listaPlatos.add(plato2);
        listaPlatos.add(plato3);
        listaPlatos.add(plato4);



    }
}
