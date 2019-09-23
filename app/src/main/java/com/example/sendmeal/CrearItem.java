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
        setContentView(R.layout.activity_crear_item);

        toolbar = (Toolbar) findViewById(R.id.toolbarCrearItem);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
                    plato.setTítulo(titulo);
                    plato.setDescripcion(descripcion);
                    plato.setPrecio(precio);
                    plato.setCalorías(calorias);
                    ///////////////////////////////
                    //GUARDAR INSTANCIA DE PLATO //
                    ///////////////////////////////
                    setResult(Activity.RESULT_OK);
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
