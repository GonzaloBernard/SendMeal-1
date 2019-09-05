package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Obtener context
        context = getApplicationContext();

        //Declaracion de views
        final SeekBar seekBar;
        final TextView textViewSeekBar;
        final LinearLayout layoutEsVendendor;
        final Switch switchEsVendedor;
        final CheckBox checkBoxAceptarTerminos;
        final Button buttonRegistrar;
        final EditText nombreUsuario;
        final EditText password;
        final EditText passwordR;
        final EditText correo;
        final EditText tarjetaNumero;
        final EditText tarjetaCCV;
        final EditText tarjetaFecha;
        final EditText nombreVendedor;
        final EditText CBUVendedor;


        //Inicializacion de views
        seekBar = (SeekBar) findViewById(R.id.seekBarCreditoInicial);
        textViewSeekBar = (TextView) findViewById(R.id.textViewSeekBar);
        layoutEsVendendor = (LinearLayout) findViewById(R.id.layoutEsVendedor);
        switchEsVendedor = (Switch) findViewById(R.id.switchEsVendedor);
        checkBoxAceptarTerminos = (CheckBox) findViewById(R.id.checkBoxAceptarTerminos);
        buttonRegistrar = (Button) findViewById(R.id.buttonRegistrar);

        //
        // LOGICA DEL SEEKBAR
        //
        //Muestra el valor por defecto del seekbar
        textViewSeekBar.setText("100");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {
                //Hace que el el seekBar vaya de 100 en 100
                progress = progress / 100;
                progress = progress * 100;
                //Muestra el estado del seekBar + 100
                textViewSeekBar.setText(progress + 100 + "");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //
        // LOGICA DEL SWITCH
        //
        switchEsVendedor.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    layoutEsVendendor.setVisibility(View.VISIBLE);
                }
                else
                    layoutEsVendendor.setVisibility(View.GONE);
        }

        });

        //
        // LOGICA DEL CHECKBOX ACEPTAR TERMINOS
        //
        checkBoxAceptarTerminos.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    buttonRegistrar.setEnabled(true);
                }
                else
                    buttonRegistrar.setEnabled(false);
            }

        });



        //
        // LOGICA DEL BUTTON ACEPTAR
        //
        buttonRegistrar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                //HACER TODAS LAS VALIDACIONES DE LOS DATOS DE ENTRADA//
                Boolean validar=false;
                ////////////////////////////////////////////////////////
                Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });

        //cierre del onCreate
    }
}