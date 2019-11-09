package com.example.sendmeal;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.Plato;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    // Tipo de login
    public static String _USUARIO = "usuario";
    public static final String KEY_VENDEDOR = "vendedor";
    public static final String KEY_COMPRADOR = "comprador";
    //  SE ESTABLECE POR DEFAULT LA IP/DIRECCION del servidor JSON
    public static String _SERVER = "10.0.2.2:3000/";
    private static Integer NUEVO_PLATO_REQUEST = 1;
    private static ArrayList<Plato> _PLATOS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        //SONIDO DE INICIO
        MediaPlayer mp = MediaPlayer.create(this, R.raw.misc222);
        mp.start();
        final EditText etServer = (EditText) findViewById(R.id.editTextServerIP);
        final Button buttonCambiarIP = (Button) findViewById(R.id.buttonServerIP);
        final LinearLayout layoutCambiarIP = (LinearLayout) findViewById(R.id.layoutServerIP);

        toolbar.setVisibility(View.INVISIBLE);
        etServer.setText(_SERVER);

        buttonCambiarIP.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                etServer.setEnabled(false);
                buttonCambiarIP.setEnabled(false);
                buttonCambiarIP.setVisibility(View.GONE);
                // SE GUARDA LA IP DEL SERVIDOR
                _SERVER="http://"+etServer.getText().toString();
                etServer.setTextColor(Color.GREEN);
                //SE HABILITAN LAS FUNCIONES DE LA APP
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
                i2.putExtra(AbmPlato._PLATO_MODO_KEY  ,  AbmPlato._KEY_CREAR_PLATO);
                startActivityForResult(i2, NUEVO_PLATO_REQUEST);
                return true;
            case R.id.toolBarListaPlatos:
                Intent i3 = new Intent(HomeActivity.this,ListaItems.class);
                //SE LE PASAN LOS PLATOS CREADOS
                i3.putExtra(AbmPlato._PLATOS_LISTA_KEY  ,  _PLATOS);
                i3.putExtra(_USUARIO , KEY_COMPRADOR);
                startActivity(i3);
                //SE LIMPIA LA LISTA PARA EVITAR ERRORES
                _PLATOS.clear();
                return true;
            case R.id.toolBarListaVendedor:
                Intent i4 = new Intent(HomeActivity.this,ListaItems.class);
                //SE LE PASAN LOS PLATOS CREADOS
                i4.putExtra(AbmPlato._PLATOS_LISTA_KEY  ,  _PLATOS);
                i4.putExtra(_USUARIO , KEY_VENDEDOR);
                startActivity(i4);
                //SE LIMPIA LA LISTA PARA EVITAR ERRORES
                _PLATOS.clear();
                return true;
            case R.id.toolBarBuscarPlatos:
                Intent i5 = new Intent(HomeActivity.this, BuscarPlatos.class);
                startActivity(i5);
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
                try {
                    Bundle extras = data.getExtras();
                    // SE OBTIENE EL NUEVO PLATO
                    Plato plato = (Plato) data.getParcelableExtra(AbmPlato._PLATO_INDIVIDUAL_KEY);
                    // SE GUARDA EL PLATO EN EL SERVIDOR
                    PlatoRepository.getInstance().crearPlato(plato, miHandler);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            else
                Toast.makeText(this,R.string.homePlatoError,Toast.LENGTH_LONG).show();
        }
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1 ){
                case PlatoRepository._ALTA_PLATO:
                    Toast.makeText(HomeActivity.this,R.string.homePlatoCreado,Toast.LENGTH_LONG).show();

                    break;
                default:break;
            }
        }
    };
}
