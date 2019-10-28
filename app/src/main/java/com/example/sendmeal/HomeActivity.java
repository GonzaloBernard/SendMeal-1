package com.example.sendmeal;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.Plato;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    public static String _SERVER = "http://10.0.2.2:3000/";
    private static Integer NUEVO_PLATO_REQUEST = 1;
    private static ArrayList<Plato> _PLATOS = new ArrayList<>();
    //KEY PARA UN ArrayList<PLATOS>
    public static final String PLATOS_LISTA_KEY = "_PLATOS";
    //KEY PARA UN PLATO SOLO
    public static final String PLATO_INDIVIDUAL_KEY = "plato";
    //KEY PARA EL MODO
    public static final String PLATO_MODO_KEY = "modo";
    //KEY's PARA LOS DISTINTOS MODOS (para el ABM)
    public static final Integer KEY_CREAR_PLATO = 1;
    public static final Integer KEY_EDITAR_PLATO = 2;
    public static final Integer KEY_CONSULTAR_PLATO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        //SONIDO DE INICIO
        MediaPlayer mp = MediaPlayer.create(this, R.raw.misc222);
        mp.start();
        final EditText server = (EditText) findViewById(R.id.editTextServerIP);
        final TextView tvServer = (TextView) findViewById(R.id.textViewServerIP);
        final Button buttonCambiarIP = (Button) findViewById(R.id.buttonServerIP);
        final LinearLayout layoutCambiarIP = (LinearLayout) findViewById(R.id.layoutServerIP);
        toolbar.setVisibility(View.INVISIBLE);
        tvServer.setText(_SERVER);

        buttonCambiarIP.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                _SERVER = server.getText().toString() ;
                tvServer.setText(_SERVER);
                //DESHABILITAR EL CAMBIO DE IP
                tvServer.setEnabled(false);
                buttonCambiarIP.setEnabled(false);
                layoutCambiarIP.setVisibility(View.INVISIBLE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()) {
            case R.id.toolBarRegistrar:
                Intent i1 = new Intent(HomeActivity.this, AbmUsuario.class);
                startActivity(i1);
                return true;
            case R.id.toolBarNuevoPlato:
                Intent i2 = new Intent(HomeActivity.this, AbmPlato.class);
                //SE INDICA QUE EL MODO ES CREAR PLATO
                i2.putExtra(HomeActivity.PLATO_MODO_KEY  ,  KEY_CREAR_PLATO);
                startActivityForResult(i2, NUEVO_PLATO_REQUEST);
                return true;
            case R.id.toolBarListaPlatos:
                Intent i3 = new Intent(HomeActivity.this,ListaItems.class);
                //SE LE PASAN LOS PLATOS CREADOS
                i3.putExtra(PLATOS_LISTA_KEY  ,  _PLATOS);
                startActivity(i3);
                //SE LIMPIA LA LISTA PARA EVITAR ERRORES
                _PLATOS.clear();
                return true;
            case R.id.toolBuscarPlatos:
                Intent i4 = new Intent(HomeActivity.this, BuscarPlatos.class);
                startActivity(i4);
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
                    Plato plato = data.getParcelableExtra(HomeActivity.PLATO_INDIVIDUAL_KEY);
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

}
