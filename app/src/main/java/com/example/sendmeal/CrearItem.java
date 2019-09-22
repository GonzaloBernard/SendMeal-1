package com.example.sendmeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sendmeal.domain.Plato;

public class CrearItem extends AppCompatActivity {

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
        setContentView(R.layout.activity_crear_item);

        toolbar = (Toolbar) findViewById(R.id.toolbarCrearItem);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button buttonGuardar = (Button) findViewById(R.id.buttonCrearPlatoGuardar);

        buttonGuardar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PLATOS HARDCODEADOS (AL IMPLEMENTAR PERSISTENCIA DE DATOS CADA PLATO SE DIRECCTAMENTE EN
                // LA BASE DE DATOS
                Intent intentResultado = new Intent();
                intentResultado.putExtra("titulo1", "Fideos con salsa");
                intentResultado.putExtra("precio1", 70);
                intentResultado.putExtra("titulo2", "Hamburguesa con queso");
                intentResultado.putExtra("precio2", 60);
                intentResultado.putExtra("titulo3", "Pizza Napolitana");
                intentResultado.putExtra("precio3", 120);
                setResult(Activity.RESULT_OK, intentResultado);
                finish();
            }
        });
    }
}
