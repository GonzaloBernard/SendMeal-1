package com.example.sendmeal;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sendmeal.domain.Plato;

public class AbmPlato extends AppCompatActivity {
    //KEY PARA EL MODO
    public static final String _ABMC_PLATO_MODO_KEY = "ABMC_MODO";
    //KEY's PARA LOS DISTINTOS MODOS (para el ABM)
    public static final Integer _KEY_CREAR_PLATO = 1;
    public static final Integer _KEY_EDITAR_PLATO = 2;
    public static final Integer _KEY_CONSULTAR_PLATO = 3;
    public static final Integer _KEY_BORRAR_PLATO = 4;
    //KEY PARA ENVIAR UN ArrayList<PLATOS>
    public static final String _PLATOS_LISTA_KEY = "_PLATOS";
    //KEY PARA ENVIAR UN PLATO
    public static final String _PLATO_INDIVIDUAL_KEY = "plato";
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
        setContentView(R.layout.activity_abm_plato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEditarPlato);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //DECLARACION DE VIEWS
        final EditText editTextID = (EditText) findViewById(R.id.EditTextEditarPlatoID);
        final EditText editTextTitulo = (EditText) findViewById(R.id.editTextEditarPlatoTitulo);
        final EditText editTextDescripcion = (EditText) findViewById(R.id.editTextEditarPlatoDescripcion);
        final EditText editTextPrecio = (EditText) findViewById(R.id.editTextEditarPlatoPrecio);
        final EditText editTextCalorias = (EditText) findViewById(R.id.editTextEditarPlatoCalorias);
        Button buttonGuardar = (Button) findViewById(R.id.buttonEditarPlatoGuardar);

        final Resources resources = getResources();
        Bundle extras = getIntent().getExtras();
        switch( extras.getInt(_ABMC_PLATO_MODO_KEY) ) {
            case 1:
                actionBar.setTitle(R.string.tituloToolbarCrearItem);
                buttonGuardar.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            //OBTENER DATOS DE ENTRADA
                            Integer id = Integer.parseInt(editTextID.getText().toString());
                            String titulo = editTextTitulo.getText().toString();
                            String descripcion = editTextDescripcion.getText().toString();
                            Double precio = Double.parseDouble(editTextPrecio.getText().toString());
                            Integer calorias = Integer.parseInt(editTextCalorias.getText().toString());
                            //VALIDACIONES
                            if (id < 0)
                                throw new Exception();
                            if (titulo.length() < 3)
                                throw new Exception(resources.getString(R.string.crearItemErrorID));
                            if (precio < 0)
                                throw new Exception(resources.getString(R.string.crearItemErrorPrecio));
                            if (calorias < 0)
                                throw new Exception(resources.getString(R.string.crearItemErrorCalorias));

                            //CREAR INSTANCIA DE PLATO CON SUS DATOS
                            Plato platoAlta = new Plato();
                            platoAlta.setId(id);
                            platoAlta.setTitulo(titulo);
                            platoAlta.setDescripcion(descripcion);
                            platoAlta.setPrecio(precio);
                            platoAlta.setCalorias(calorias);
                            platoAlta.setImagen(R.drawable.hamburguesa);
                            platoAlta.setEnOferta(false);
                            ///////////////////////////////
                            //DEVOLVER DATOS A HOME ACTIVITY //
                            ///////////////////////////////

                            Intent intentResultado = new Intent();
                            intentResultado.putExtra(_PLATO_INDIVIDUAL_KEY,platoAlta);
                            setResult(Activity.RESULT_OK, intentResultado);
                            finish();
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(AbmPlato.this,resources.getString(R.string.crearItemErrorCamposNumericos),Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(AbmPlato.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            ///////////////////
            //  EDITAR PLATO //
            ///////////////////
            case 2:
                actionBar.setTitle(R.string.tituloToolbarModificarPlato);
                // SE OBTIENE EL PLATO A MODIFICAR
                final Plato platoModificacion = (Plato) extras.getParcelable(_PLATO_INDIVIDUAL_KEY);
                //SE LLENAN LOS VIEWS CON LA INFORMACION DEL PLATO A MODIFICAR
                editTextID.setText(platoModificacion.getId().toString());
                editTextID.setEnabled(false);
                editTextTitulo.setText(platoModificacion.getTitulo());
                editTextDescripcion.setText(platoModificacion.getDescripcion());
                editTextPrecio.setText(platoModificacion.getPrecio().toString());
                editTextCalorias.setText(platoModificacion.getCalorias().toString());
                buttonGuardar.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //VARIABLES QUE CONTENDRAN LOS NUEVOS DATOS
                        String titulo;
                        String descripcion;
                        Double precio;
                        Integer calorias;
                        try {
                            //OBTENER DATOS DE ENTRADA
                            titulo = editTextTitulo.getText().toString();
                            descripcion = editTextDescripcion.getText().toString();
                            precio = Double.parseDouble(editTextPrecio.getText().toString());
                            calorias = Integer.parseInt(editTextCalorias.getText().toString());

                            //VALIDACIONES
                            if (titulo.length() < 3)
                                throw new Exception(resources.getString(R.string.crearItemErrorID));
                            if (precio < 0)
                                throw new Exception(resources.getString(R.string.crearItemErrorPrecio));
                            if (calorias < 0)
                                throw new Exception(resources.getString(R.string.crearItemErrorCalorias));

                            //SE ACTUALIZAN LOS DATOS DEL PLATO
                            platoModificacion.setTitulo(titulo);
                            platoModificacion.setDescripcion(descripcion);
                            platoModificacion.setPrecio(precio);
                            platoModificacion.setCalorias(calorias);
                            //SE CREA UN INTENT Y SE AGREGA EL PLATO
                            Intent intentResultado = new Intent();
                            intentResultado.putExtra(_PLATO_INDIVIDUAL_KEY, platoModificacion);
                            setResult(Activity.RESULT_OK, intentResultado);
                            //FIN DE LA ACTIVITY FOR RESULT
                            finish();
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(AbmPlato.this,resources.getString(R.string.crearItemErrorCamposNumericos),Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(AbmPlato.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });


                break;
            ///////////////////
            //  CONSULTAR PLATO //
            ///////////////////
            case 3:
                actionBar.setTitle(R.string.tituloToolbarConsultarPlato);
                // SE OBTIENE EL PLATO A CONSULTAR
                Plato platoConsulta = (Plato) extras.getParcelable(_PLATO_INDIVIDUAL_KEY);
                //SE LLENAN LOS VIEWS CON LA INFORMACION DEL PLATO A MODIFICAR
                editTextID.setText(platoConsulta.getId().toString());
                editTextID.setEnabled(false);
                editTextTitulo.setText(platoConsulta.getTitulo());
                editTextTitulo.setEnabled(false);
                editTextDescripcion.setText(platoConsulta.getDescripcion());
                editTextDescripcion.setEnabled(false);
                editTextPrecio.setText(platoConsulta.getPrecio().toString());
                editTextPrecio.setEnabled(false);
                editTextCalorias.setText(platoConsulta.getCalorias().toString());
                editTextCalorias.setEnabled(false);
                buttonGuardar.setVisibility(View.INVISIBLE);
                break;
            case 4:
                //SE RECUPERA EL PLATO A BORRAR
                final Plato platoBorrar = (Plato) extras.getParcelable(_PLATO_INDIVIDUAL_KEY);
                //SE CREA UN INTENT Y SE AGREGA EL PLATO
                Intent intentResultado = new Intent();
                intentResultado.putExtra(_PLATO_INDIVIDUAL_KEY, platoBorrar);
                setResult(Activity.RESULT_OK, intentResultado);
                //FIN DE LA ACTIVITY FOR RESULT
                finish();
                break;

            default:;
        }
    }
}
