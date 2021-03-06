package com.example.sendmeal;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.Plato;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class HomeActivity extends AppCompatActivity {

    // Tipo de login
    public static final String _TIPO_USUARIO = "usuario";
    public static final String KEY_VENDEDOR = "vendedor";
    public static final String KEY_COMPRADOR = "comprador";
    static final String MAPA_KEY = "MAPA_KEY";
    static final String KEY_MAPA_ENVIOS = "KEY_MAPA_ENVIOS";
    static final String KEY_MAPA_UBICACION = "KEY_MAPA_UBICACION";
    //  SE ESTABLECE POR DEFAULT LA IP/DIRECCION del servidor JSON
    //static String _SERVER = "192.168.0.22:3000/";
    static String _SERVER = "10.15.153.131:3000/";
    //static String _SERVER = "10.0.2.2:3000/";
    private static Integer NUEVO_PLATO_REQUEST = 1;

    public static String getTipoUsuario() {
        return _TIPO_USUARIO;
    }

    public static String getServer() {
        return _SERVER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);

        // Se guarda el token en las preferencias en caso de que sea un  nuevo usuario
        getUserToken();

        /*
        //SONIDO DE INICIO
        MediaPlayer mp = MediaPlayer.create(this, R.raw.miscloltrl);
        mp.start();
         */
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
                i2.putExtra(AbmPlato._ABMC_PLATO_MODO_KEY,  AbmPlato._KEY_CREAR_PLATO);
                startActivityForResult(i2, NUEVO_PLATO_REQUEST);
                return true;
            case R.id.toolBarListaPlatos:
                Intent i3 = new Intent(HomeActivity.this,ListaItems.class);
                //SE INDICA EL TIPO DE LISTA A MOSTRAR
                i3.putExtra(_TIPO_USUARIO, KEY_COMPRADOR);
                startActivity(i3);
                return true;
            case R.id.toolBarListaVendedor:
                Intent i4 = new Intent(HomeActivity.this,ListaItems.class);
                i4.putExtra(_TIPO_USUARIO, KEY_VENDEDOR);
                startActivity(i4);
                return true;
            case R.id.toolBarMapaPedidos:
                Intent i5 = new Intent(HomeActivity.this,MapaActivity.class);
                //i5.putExtra(_TIPO_USUARIO, KEY_VENDEDOR);
                i5.putExtra(MAPA_KEY,KEY_MAPA_ENVIOS);
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

    private String getUserToken() {
        // RECUPERAR TOKEN O CREARLO
        String TOKEN = getTokenFromPrefs();
        if (TOKEN == null) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String newToken = instanceIdResult.getToken();
                            saveTokenToPrefs(newToken);
                            Log.e("newToken", newToken);
                        }
                    });
        }
        return TOKEN;
    }

    private void saveTokenToPrefs(String _token){
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("registration_id", _token);
        editor.apply();
    }
    private String getTokenFromPrefs(){
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }
}