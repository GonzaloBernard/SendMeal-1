package com.example.sendmeal;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.sendmeal.domain.Plato;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private static Integer NUEVO_PLATO_REQUEST = 1;
    private static ArrayList<Plato> _PLATOS = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        //SONIDO DE INICIO
        MediaPlayer mp = MediaPlayer.create(this, R.raw.misc222);
        mp.start();
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
                Intent i2 = new Intent(HomeActivity.this, ABMPlato.class);
                //SE INDICA QUE EL MODO ES CREAR PLATO ( CREAR=0 EDITAR=1 CONSULTAR=2 )
                i2.putExtra("modo",0);
                startActivityForResult(i2, NUEVO_PLATO_REQUEST);
                return true;
            case R.id.toolBarListaPlatos:
                Intent i3 = new Intent(HomeActivity.this,ListaItems.class);
                //SE LE PASAN LOS PLATOS CREADOS
                i3.putExtra("_PLATOS",_PLATOS);
                startActivity(i3);
                //SE LIMPIA LA LISTA PARA EVITAR ERRORES
                _PLATOS.clear();
                return true;
            default:
                Toast.makeText(this,". . . . ",Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        if (requestCode == NUEVO_PLATO_REQUEST) {
            if (resultCode == RESULT_OK) {
                // OBTENIENDO EL NUEVO PLATO
                try {
                    Plato plato = (Plato) data.getSerializableExtra("plato");
                    //SE VUELVE A CARGAR EL LA LISTA
                    _PLATOS.add(plato);
                    Toast.makeText(this,R.string.homePlatoCreado ,Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                }
            else
                Toast.makeText(this,R.string.homePlatoError,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

    }

}
