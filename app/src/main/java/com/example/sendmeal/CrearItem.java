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

public class CrearItem extends AppCompatActivity {

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
        setContentView(R.layout.activity_crear_item);

        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCrearItem);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tituloToolbarCrearItem);
        } catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        final EditText editTextId = (EditText) findViewById(R.id.editTextCrearPlatoID);
        final EditText editTextTitulo = (EditText) findViewById(R.id.editTextCrearPlatoTitulo);
        final EditText editTextDescripcion = (EditText) findViewById(R.id.editTextCrearPlatoDescripcion);
        final EditText editTextPrecio = (EditText) findViewById(R.id.editTextCrearPlatoPrecio);
        final EditText editTextCalorias = (EditText) findViewById(R.id.editTextCrearPlatoCalorias);
        Button buttonGuardar = (Button) findViewById(R.id.buttonCrearPlatoGuardar);
        final Resources resources = getResources();

        buttonGuardar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    //OBTENER DATOS DE ENTRADA
                    Integer id = Integer.parseInt(editTextId.getText().toString());
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
                    ///////////////////////////////
                    //DEVOLVER DATOS A HOME ACTIVITY //
                    ///////////////////////////////
                    Intent intentResultado = new Intent();
                    intentResultado.putExtra("plato",plato);
                    setResult(Activity.RESULT_OK, intentResultado);
                    finish();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(CrearItem.this,resources.getString(R.string.crearItemErrorCamposNumericos),Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(CrearItem.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
