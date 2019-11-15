package com.example.sendmeal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BuscarPlatos extends AppCompatActivity {

    private Context context;

    @SuppressLint("ResourceType")

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
        setContentView(R.layout.activity_buscar_platos);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBuscarItems);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tituloToolbarBuscarItems);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        context = getApplicationContext();
        //////////////////////////
        // Declaracion de views //
        //////////////////////////
        final TextView textViewNombrePlato;
        final TextView textViewPrecioMax;
        final TextView textViewPrecioMin;
        final EditText editTextNombrePlato;
        final EditText editTextPrecioMax;
        final EditText editTextPrecioMin;
        final Button buttonBuscar;


        /////////////////////////////
        // Inicializacion de views //
        /////////////////////////////

        buttonBuscar = (Button) findViewById(R.id.buttonBuscar);
        //textViewNombrePlato = (TextView) findViewById(R.id.textViewNombrePlato);
        //textViewPrecioMax = (TextView) findViewById(R.id.textViewPrecioMax);
        //textViewPrecioMin = (TextView) findViewById(R.id.textViewPrecioMin);
        editTextNombrePlato = (EditText) findViewById(R.id.editTextEditarNombrePlato);
        editTextPrecioMax = (EditText) findViewById(R.id.editTextPrecioMax);
        editTextPrecioMin = (EditText) findViewById(R.id.editTextPrecioMin);


        buttonBuscar.setOnClickListener(new Button.OnClickListener() {

            Double precioMax, precioMin;

            @Override
            public void onClick(View view) {

                //Asignacion de precio max
                if(editTextPrecioMax.getText().toString().isEmpty()){
                    precioMax=100000000000000.0;
                }else{
                    precioMax = Double.parseDouble(editTextPrecioMax.getText().toString());
                }

                //Asignacion de precio min
                if(editTextPrecioMin.getText().toString().isEmpty()){
                    precioMin=0.0;
                }else{
                    precioMin = Double.parseDouble(editTextPrecioMin.getText().toString());
                }

                //CONTROL DE QUE PRECIO MIN < PRECIO MAX
                if(precioMin > precioMax){
                        Toast.makeText(context, "Error, ingrese precios validos", Toast.LENGTH_SHORT).show();
                }else{

                    Intent i3 = new Intent(BuscarPlatos.this, ListaItems.class);
                    i3.putExtra(HomeActivity._TIPO_USUARIO, HomeActivity.KEY_COMPRADOR);
                    i3.putExtra("FILTRO", "");
                    i3.putExtra("titulo", editTextNombrePlato.getText().toString());
                    i3.putExtra("precioMax", precioMax);
                    i3.putExtra("precioMin", precioMin);
                    i3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i3);
                    finish();
                }

            }
        });
    }
}
