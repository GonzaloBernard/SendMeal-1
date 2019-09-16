package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolBarRegistrar:
                Intent i1 = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i1);
                return true;
            case R.id.toolBarNuevoPlato:
                Intent i2 = new Intent(HomeActivity.this,CrearItem.class);
                startActivity(i2);
                return true;
            case R.id.toolBarListaPlatos:
                Intent i3 = new Intent(HomeActivity.this,ListaItems.class);
                startActivity(i3);
                return true;
            default:
                Toast.makeText(this,". . . . ",Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
    }

}
