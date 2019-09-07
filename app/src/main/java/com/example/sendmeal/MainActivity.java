package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
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
    private SeekBar seekBar;
    private TextView textViewSeekBar;
    private LinearLayout layoutEsVendendor;
    private Switch switchEsVendedor;
    private CheckBox checkBoxAceptarTerminos;
    private Button buttonRegistrar;
    private EditText nombreUsuario;
    private EditText password;
    private EditText passwordR;
    private EditText correo;
    private EditText tarjetaNumero;
    private EditText tarjetaCCV;
    private EditText tarjetaFecha;
    private EditText nombreVendedor;
    private EditText CBUVendedor;
    private TextView textViewErrorPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Obtener context
        context = getApplicationContext();

        //Declaracion de views



        //Inicializacion de views
        seekBar = (SeekBar) findViewById(R.id.seekBarCreditoInicial);
        textViewSeekBar = (TextView) findViewById(R.id.textViewSeekBar);
        layoutEsVendendor = (LinearLayout) findViewById(R.id.layoutEsVendedor);
        switchEsVendedor = (Switch) findViewById(R.id.switchEsVendedor);
        checkBoxAceptarTerminos = (CheckBox) findViewById(R.id.checkBoxAceptarTerminos);
        buttonRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        textViewErrorPass = (TextView) findViewById(R.id.textViewErrorPass);

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
                nombreUsuario = (EditText) findViewById(R.id.editTextNumero);
                password = (EditText) findViewById(R.id.editTextPassword);
                passwordR = (EditText) findViewById(R.id.editTextPasswordR);
                correo = (EditText) findViewById(R.id.editTextCorreo);
                CBUVendedor = (EditText) findViewById(R.id.editTextCBU);
                tarjetaCCV = (EditText) findViewById(R.id.editTextCCV);
                tarjetaNumero = (EditText) findViewById(R.id.editTextNumero);
                tarjetaFecha = (EditText) findViewById(R.id.editTextFechaTarjeta);


                if(!password.getText().equals(passwordR.getText())){
                  Toast.makeText(context, "Las claves no coinciden", Toast.LENGTH_SHORT).show();
                  textViewErrorPass.setVisibility(View.VISIBLE);
                  passwordR.requestFocus();
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(correo.getText()).matches()){
                    
                }
                ////////////////////////////////////////////////////////

            }
        });

        passwordR.setOnClickListener(new EditText.OnClickListener(){

            @Override
            public void onClick(View view) {

                textViewErrorPass.setVisibility(View.INVISIBLE);
            }
        });


        //cierre del onCreate
    }
}