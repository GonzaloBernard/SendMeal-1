package com.example.sendmeal;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sendmeal.domain.Plato;
import java.util.ArrayList;
import java.util.List;

public class ListaItems extends AppCompatActivity {

    private static final int REQUEST_CODE_EDITAR_PLATO = 2;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static List<Plato> _PLATOS = new ArrayList<>();

    public static List<Plato> getPlatos() {
        return _PLATOS;
    }
    private Plato getPlatoById(Integer id){

        for(Plato plato: _PLATOS){
            if(plato.getId().equals(id)){
                return plato;
            }
        }

        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if( resultCode== Activity.RESULT_OK){
            if(requestCode==REQUEST_CODE_EDITAR_PLATO){
                // OBTENIENDO LOS DATOS DEL PLATO MODIFICADO
                Bundle extras = data.getExtras();
                Integer id = extras.getInt("idPlato");
                String titulo = extras.getString("titulo");
                String descripcion = extras.getString("descripcion");
                Double precio = extras.getDouble("precio");
                Integer calorias = extras.getInt("calorias");
                // RECUPERANDO EL PLATO POR ID
                Plato plato = getPlatoById(id);
                //SE SACA DE LA LISTA PARA ACTUALIZARLO
                _PLATOS.remove(plato);
                //SE SETEAN LOS CAMBIOS
                plato.setTitulo(titulo);
                plato.setDescripcion(descripcion);
                plato.setPrecio(precio);
                plato.setCalorias(calorias);
                //SE VUELVE A CARGAR EL LA LISTA
                _PLATOS.add(plato);
                Toast.makeText(this,R.string.listaItemsPlatoEditado ,Toast.LENGTH_LONG).show();
                //SE LE DICE AL ADAPTER QUE ACTUALICE LA DATA
                mAdapter.notifyDataSetChanged();
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
        toolbar = (Toolbar) findViewById(R.id.toolbarListaItems);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.tituloToolbarListarItems);


        ArrayList<Plato> platosNuevos = (ArrayList<Plato>) getIntent().getSerializableExtra("_PLATOS");
        _PLATOS.addAll(platosNuevos);


        // PLATOS HARDCODEADOS EN CASO DE NO CREAR NINGUN PLATO MANUEALMENTE
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
            plato2.setId(2);
            plato2.setTitulo("Pizza napolitana");
            plato2.setDescripcion("Muzzarela, tomate y oregano");
            plato2.setPrecio(150d);
            plato2.setCalorias(1200);
            plato2.setImagen(R.drawable.pizza);
            plato3.setId(3);
            plato3.setTitulo("Triples de miga");
            plato3.setDescripcion("Sandwich de miga con jamos queso lechuga y tomate");
            plato3.setPrecio(100d);
            plato3.setCalorias(600);
            plato3.setImagen(R.drawable.sandwich);
            plato4.setId(4);
            plato4.setTitulo("Lomo gratinado");
            plato4.setDescripcion("Sandwich de lomo con queso derretido");
            plato4.setPrecio(200d);
            plato4.setCalorias(1300);
            plato4.setImagen(R.drawable.lomito);

            //LISTA DE PLATOS

            _PLATOS.add(plato1);
            _PLATOS.add(plato2);
            _PLATOS.add(plato3);
            _PLATOS.add(plato4);
        }
        ////////////////////////////////////////////////////////////////////////

        mRecyclerView = (RecyclerView) findViewById(R.id.listaItemsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PlatoAdapter( _PLATOS );
        mRecyclerView.setAdapter(mAdapter);


    }
}
