package com.example.sendmeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        toolbar = (Toolbar) findViewById(R.id.toolbarListaItems);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // PLATOS HARDCODEADOS (TENIENDO LA BASE DE DATOS SE LEVANTAN DIRECTAMENTE DE AHI)
        String titulo1 = getIntent().getExtras().getString("titulo1");
        String titulo2 = getIntent().getExtras().getString("titulo2");
        String titulo3 = getIntent().getExtras().getString("titulo3");
        Integer precio1 = getIntent().getExtras().getInt("precio1");
        Integer precio2 = getIntent().getExtras().getInt("precio2");
        Integer precio3 = getIntent().getExtras().getInt("precio3");



    }
}
