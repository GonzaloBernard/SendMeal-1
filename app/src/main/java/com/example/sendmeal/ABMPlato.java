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

public class ABMPlato extends AppCompatActivity {

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
            actionBar.setTitle(R.string.tituloToolbarModificarPlato);
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

        Integer modo = getIntent().getExtras().getInt("modo");
        // SE CREA UN PLATO VACIO
        final Plato plato;
        switch(modo) {
            case 0:
                Toast.makeText(ABMPlato.this,"CREAR",Toast.LENGTH_LONG).show();
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
                            Plato plato = new Plato();
                            plato.setId(id);
                            plato.setTitulo(titulo);
                            plato.setDescripcion(descripcion);
                            plato.setPrecio(precio);
                            plato.setCalorias(calorias);
                            plato.setImagen(R.drawable.hamburguesa);
                            plato.setEnOferta(false);
                            ///////////////////////////////
                            //DEVOLVER DATOS A HOME ACTIVITY //
                            ///////////////////////////////
                            Intent intentResultado = new Intent();
                            intentResultado.putExtra("plato",plato);
                            setResult(Activity.RESULT_OK, intentResultado);
                            finish();
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(ABMPlato.this,resources.getString(R.string.crearItemErrorCamposNumericos),Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(ABMPlato.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            ///////////////////
            //  EDITAR PLATO //
            ///////////////////
            case 1:
                Toast.makeText(ABMPlato.this,"EDITAR",Toast.LENGTH_LONG).show();
                actionBar.setTitle(R.string.tituloToolbarModificarPlato);
                // SE OBTIENE EL PLATO A MODIFICAR
                plato = (Plato) getIntent().getSerializableExtra("plato");
                //SE LLENAN LOS VIEWS CON LA INFORMACION DEL PLATO A MODIFICAR
                editTextID.setText(plato.getId().toString());
                editTextID.setEnabled(false);
                editTextTitulo.setText(plato.getTitulo());
                editTextDescripcion.setText(plato.getDescripcion());
                editTextPrecio.setText(plato.getPrecio().toString());
                editTextCalorias.setText(plato.getCalorias().toString());
                buttonGuardar.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //VARIABLES QUE CONTENDRAN LOS NUEVOS DATOS
                        Integer idPlato;
                        String titulo;
                        String descripcion;
                        Double precio;
                        Integer calorias;
                        try {
                            //OBTENER DATOS DE ENTRADA
                            idPlato = Integer.parseInt(editTextID.getText().toString());
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
                            plato.setTitulo(titulo);
                            plato.setDescripcion(descripcion);
                            plato.setPrecio(precio);
                            plato.setCalorias(calorias);
                            //SE CREA UN INTENT Y SE AGREGA EL PLATO
                            Intent intentResultado = new Intent();
                            intentResultado.putExtra("plato", plato);
                            setResult(Activity.RESULT_OK, intentResultado);
                            //FIN DE LA ACTIVITY FOR RESULT
                            finish();
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(ABMPlato.this,resources.getString(R.string.crearItemErrorCamposNumericos),Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(ABMPlato.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });


                break;
            ///////////////////
            //  EDITAR PLATO //
            ///////////////////
            default:
                Toast.makeText(ABMPlato.this,"CONSULTAR",Toast.LENGTH_LONG).show();
                actionBar.setTitle(R.string.tituloToolbarConsultarPlato);
                // SE OBTIENE EL PLATO A CONSULTAR
                plato = (Plato) getIntent().getSerializableExtra("plato");
                //SE LLENAN LOS VIEWS CON LA INFORMACION DEL PLATO A MODIFICAR
                editTextID.setText(plato.getId().toString());
                editTextID.setEnabled(false);
                editTextTitulo.setText(plato.getTitulo());
                editTextTitulo.setEnabled(false);
                editTextDescripcion.setText(plato.getDescripcion());
                editTextDescripcion.setEnabled(false);
                editTextPrecio.setText(plato.getPrecio().toString());
                editTextPrecio.setEnabled(false);
                editTextCalorias.setText(plato.getCalorias().toString());
                editTextCalorias.setEnabled(false);
                buttonGuardar.setVisibility(View.INVISIBLE);
                break;
        }




    }


}
