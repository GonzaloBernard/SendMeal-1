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
    private static Integer NUEVO_PLATO_REQUEST = 1;

    // PLATOS HARDCODEADOS (AL IMPLEMENTAR BASE DE DATOS ESTO NO VA MAS)
    private String titulo1;
    private Integer precio1;
    private String titulo2;
    private Integer precio2;
    private String titulo3;
    private Integer precio3;

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
                startActivityForResult(i2, NUEVO_PLATO_REQUEST);
                return true;
            case R.id.toolBarListaPlatos:
                Intent i3 = new Intent(HomeActivity.this,ListaItems.class);

                // PLATOS HARDCODEADOS (AL IMPLEMENTAR BASE DE DATOS ESTO NO VA MAS)
                i3.putExtra("titulo1",titulo1);
                i3.putExtra("precio1",precio1);
                i3.putExtra("titulo2",titulo2);
                i3.putExtra("precio2",precio2);
                i3.putExtra("titulo3",titulo3);
                i3.putExtra("precio3",precio3);




                startActivity(i3);
                return true;
            default:
                Toast.makeText(this,". . . . ",Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (requestCode == NUEVO_PLATO_REQUEST) {
            if (resultCode == RESULT_OK) {

                // PLATOS HARDCODEADOS (AL IMPLEMENTAR BASE DE DATOS ESTO NO VA MAS)
                titulo1 = resultIntent.getExtras().getString("titulo1");
                precio1 = resultIntent.getExtras().getInt("precio1");
                titulo2 = resultIntent.getExtras().getString("titulo2");
                precio2 = resultIntent.getExtras().getInt("precio2");
                titulo3 = resultIntent.getExtras().getString("titulo3");
                precio3 = resultIntent.getExtras().getInt("precio3");


            }
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
