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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendmeal.domain.Plato;

public class EditarPlato extends AppCompatActivity {

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
        setContentView(R.layout.activity_editar_plato);

        toolbar = (Toolbar) findViewById(R.id.toolbarEditarPlato);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.tituloToolbarModificarPlato);

        final TextView textViewID = (TextView) findViewById(R.id.TextViewEditarPlatoID);
        final EditText editTextTitulo = (EditText) findViewById(R.id.editTextEditarPlatoTitulo);
        final EditText editTextDescripcion = (EditText) findViewById(R.id.editTextEditarPlatoDescripcion);
        final EditText editTextPrecio = (EditText) findViewById(R.id.editTextEditarPlatoPrecio);
        final EditText editTextCalorias = (EditText) findViewById(R.id.editTextEditarPlatoCalorias);
        Button buttonGuardar = (Button) findViewById(R.id.buttonEditarPlatoGuardar);
        final Resources resources = getResources();

        Bundle extras = getIntent().getExtras();
        final Integer idPlato = extras.getInt("idPlato");
        String titulo = extras.getString("titulo");
        String descripcion = extras.getString("descripcion");
        Double precio = extras.getDouble("precio");
        Integer calorias = extras.getInt("calorias");

        textViewID.setText(idPlato.toString());
        editTextTitulo.setText(titulo);
        editTextDescripcion.setText(descripcion);
        editTextPrecio.setText(precio.toString());
        editTextCalorias.setText(calorias.toString());


        buttonGuardar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                    //RECUPERAR INSTANCIA DE PLATO

                    ///////////////////////////////
                    //ACTUALIZAR INSTANCIA DE PLATO //
                    ///////////////////////////////
                    //CREAR INSTANCIA DE PLATO CON SUS DATOS
                    Plato plato = new Plato();
                    plato.setId(idPlato);
                    plato.setTitulo(titulo);
                    plato.setDescripcion(descripcion);
                    plato.setPrecio(precio);
                    plato.setCalorias(calorias);

                    Intent intentResultado = new Intent();
                    intentResultado.putExtra("idPlato", idPlato);
                    intentResultado.putExtra("titulo", titulo);
                    intentResultado.putExtra("descripcion", descripcion);
                    intentResultado.putExtra("precio", precio);
                    intentResultado.putExtra("calorias", calorias);
                    setResult(Activity.RESULT_OK, intentResultado);
                    finish();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(EditarPlato.this,resources.getString(R.string.crearItemErrorCamposNumericos),Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(EditarPlato.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}
