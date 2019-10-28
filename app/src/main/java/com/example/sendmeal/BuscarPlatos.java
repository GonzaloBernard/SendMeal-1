package com.example.sendmeal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_platos);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMainActivity);
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
        final String errorMaxMin = "Error, el valor mínimo debe ser menor al valor máximo"";

        /////////////////////////////
        // Inicializacion de views //
        /////////////////////////////

        buttonBuscar = (Button) findViewById(R.id.buttonBuscar);
        textViewNombrePlato = (TextView) findViewById(R.id.textViewNombrePlato);
        textViewPrecioMax = (TextView) findViewById(R.id.textViewPrecioMax);
        textViewPrecioMin = (TextView) findViewById(R.id.textViewPrecioMin);
        editTextNombrePlato = (EditText) findViewById(R.id.editTextEditarNombrePlato);
        editTextPrecioMax = (EditText) findViewById(R.id.editTextPrecioMax);
        editTextPrecioMin = (EditText) findViewById(R.id.editTextPrecioMin);


        buttonBuscar.setOnClickListener(new Button.OnClickListener() {

            int precioMax, precioMin;


            @Override
            public void onClick(View view) {
                if(!editTextPrecioMax.getText().toString().isEmpty() && !editTextPrecioMin.getText().toString().isEmpty()){

                    precioMax = Integer.parseInt(editTextPrecioMax.getText().toString());
                    precioMin = Integer.parseInt(editTextPrecioMin.getText().toString());

                    if(precioMin > precioMax){
                        Toast.makeText(context, errorMaxMin, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
