package com.example.sendmeal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDate;

public class AbmUsuario extends AppCompatActivity {

    private Context context;

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
        setContentView(R.layout.activity_abm_usuario);
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMainActivity);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tituloToolbarMainActivity);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        /////////////////////
        // Obtener context //
        /////////////////////
        context = getApplicationContext();
        //////////////////////////
        // Declaracion de views //
        //////////////////////////
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
        final EditText tarjetaVencimiento;
        final EditText nombreVendedor;
        final EditText CBUVendedor;
        final RadioGroup radioGroupTipoCuenta;
        final RadioButton esBase;
        final RadioButton esPremium;
        final RadioButton esFull;
        final EditText alias;
        final TextView errorPass;
        final TextView errorPassR;
        final TextView errorCorreo;
        final TextView errorTarjetaNumero;
        final TextView errorCCV;
        final TextView errorTarjetaVencimiento;
        final TextView errorTipoCuenta;
        final TextView errorAlias;
        final TextView errorCBU;


        /////////////////////////////
        // Inicializacion de views //
        /////////////////////////////
        radioGroupTipoCuenta = (RadioGroup) findViewById(R.id.radioGroupTipoCuenta);
        seekBar = (SeekBar) findViewById(R.id.seekBarCreditoInicial);
        textViewSeekBar = (TextView) findViewById(R.id.textViewSeekBar);
        layoutEsVendendor = (LinearLayout) findViewById(R.id.layoutEsVendedor);
        switchEsVendedor = (Switch) findViewById(R.id.switchEsVendedor);
        checkBoxAceptarTerminos = (CheckBox) findViewById(R.id.checkBoxAceptarTerminos);
        buttonRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        nombreUsuario = (EditText) findViewById(R.id.editTextTarjetaNumero);
        password = (EditText) findViewById(R.id.editTextPassword);
        passwordR = (EditText) findViewById(R.id.editTextPasswordR);
        correo = (EditText) findViewById(R.id.editTextCorreo);
        CBUVendedor = (EditText) findViewById(R.id.editTextCBU);
        tarjetaCCV = (EditText) findViewById(R.id.editTextCCV);
        tarjetaNumero = (EditText) findViewById(R.id.editTextTarjetaNumero);
        tarjetaVencimiento = (EditText) findViewById(R.id.editTextTarjetaVencimiento);

        ///////////////////
        // RADIO BUTTONS //
        ///////////////////
        esBase = (RadioButton) findViewById(R.id.radioButtonBase);
        esPremium = (RadioButton) findViewById(R.id.radioButtonPremium);
        esFull = (RadioButton) findViewById(R.id.radioButtonFull);
        alias = (EditText) findViewById(R.id.editTextAias);
        ///////////////////////////////////////////
        // MENSAJES DE ERROR EN CAMPOS A VALIDAR //
        ///////////////////////////////////////////
        errorPass = (TextView) findViewById(R.id.textViewErrorPass);
        errorPassR = (TextView) findViewById(R.id.textViewErrorPassR);
        errorCorreo = (TextView) findViewById(R.id.textViewErrorCorreo);
        errorTarjetaNumero = (TextView) findViewById(R.id.textViewErrorTarjetaNumero);
        errorCCV = (TextView) findViewById(R.id.textViewErrorCCV);
        errorTarjetaVencimiento = (TextView) findViewById(R.id.textViewErrorTarjetaVencimiento);
        errorTipoCuenta = (TextView) findViewById(R.id.textViewErrorTipoCuenta);
        errorAlias = (TextView) findViewById(R.id.textViewErrorAliasVendedor);
        errorCBU = (TextView) findViewById(R.id.textViewErrorCBU);

        ////////////////////////
        // LOGICA DEL SEEKBAR //
        ////////////////////////
        // Muestra el valor por defecto del seekbar
        radioGroupTipoCuenta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(esPremium.isChecked()){
                    textViewSeekBar.setText("250");
                    seekBar.setProgress(250);
                }else if(esFull.isChecked()){
                    textViewSeekBar.setText("500");
                    seekBar.setProgress(500);
                }else {
                    textViewSeekBar.setText("100");
                    seekBar.setProgress(100);

                }
            }
        });

        textViewSeekBar.setText("0");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {
                //Hace que el el seekBar vaya de 50 en 50

                progress = progress / 50;
                progress = progress * 50;
                //Muestra el estado del seekBar + 50
                System.out.println(progress);
                textViewSeekBar.setText(progress + "");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ///////////////////////
        // LOGICA DEL SWITCH //
        ///////////////////////
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

        //////////////////////////////////////////
        // LOGICA DEL NUMERO DE LA TARJETA //       Anda, lo saque de "la interné" ejmeplo 3 de ---> https://www.flipandroid.com/formato-de-tarjeta-de-crdito-en-editar-texto-en-android.html
        //////////////////////////////////////////
        tarjetaNumero.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void afterTextChanged(Editable s) { // Remove all spacing char
                int pos = 0;
                while (pos < s.length()) {

                    if (space == s.charAt(pos) && (((pos + 1) % 5) != 0 || pos + 1 == s.length())) {
                        s.delete(pos, pos + 1);
                    }else { pos++; }
                } // Insert char where needed.

                pos = 4;
                while (pos < s.length()) {

                    final char c = s.charAt(pos); // Only if its a digit where there should be a space we insert a space
                    if ("0123456789".indexOf(c) >= 0) {
                        s.insert(pos, "" + space);
                    }

                    pos += 5;
                }
            }
        });
        //////////////////////////////////////////
        // LOGICA DEL VENCIMIENTO DE LA TARJETA //
        //////////////////////////////////////////
        tarjetaVencimiento.addTextChangedListener(new TextWatcher() {
            int len=0;
            @Override
            public void afterTextChanged(Editable s) {
                String str = tarjetaVencimiento.getText().toString();
                if(str.length()==2&& len<str.length()){//len check for backspace
                    tarjetaVencimiento.append("/");
                }
 
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                String str = tarjetaVencimiento.getText().toString();
                len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = tarjetaVencimiento.getText().toString();
                if (str.length()==2 && before==1){
                    tarjetaVencimiento.dispatchKeyEvent(new KeyEvent(0,0, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL,0));
                }
            }

        });
        //////////////////////////////////////////
        // LOGICA DEL CHECKBOX ACEPTAR TERMINOS //
        //////////////////////////////////////////
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


        ///////////////////////////////
        // LOGICA DEL BUTTON ACEPTAR //
        ///////////////////////////////
        buttonRegistrar.setOnClickListener(new Button.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                //////////////////////////////////////
                // LIMPIAR TODOS LOS textViewErrors //
                //////////////////////////////////////
                errorCorreo.setVisibility(View.GONE);
                errorPass.setVisibility(View.GONE);
                errorPassR.setVisibility(View.GONE);
                errorTipoCuenta.setVisibility(View.GONE);
                errorAlias.setVisibility(View.GONE);
                errorCBU.setVisibility(View.GONE);
                errorTarjetaNumero.setVisibility(View.GONE);
                errorTarjetaVencimiento.setVisibility(View.GONE);
                errorCCV.setVisibility(View.GONE);

                //////////////////////////////////////////
                // VALIDACIONES DE LOS DATOS DE ENTRADA //
                //////////////////////////////////////////
                Boolean validar;
                validar=true;

                ///////////////////////////////////
                // Validaciones de campos vacios //
                ///////////////////////////////////
                if(correo.getText().toString().isEmpty()){
                    errorCorreo.setText(R.string.campoObligatorio);
                    errorCorreo.setVisibility(View.VISIBLE);
                    validar=false;
                }
                if(password.getText().toString().isEmpty()){
                    errorPass.setText(R.string.campoObligatorio);
                    errorPass.setVisibility(View.VISIBLE);
                    validar=false;
                }
                if(tarjetaNumero.getText().toString().isEmpty()){
                    errorTarjetaNumero.setText(R.string.campoObligatorio);
                    errorTarjetaNumero.setVisibility(View.VISIBLE);
                    validar=false;
                }
                if(!(esBase.isChecked() || esPremium.isChecked() || esFull.isChecked()) ){
                    errorTipoCuenta.setText(R.string.elijaTipoCuenta);
                    errorTipoCuenta.setVisibility(View.VISIBLE);
                    validar=false;
                }
                if(switchEsVendedor.isChecked()){
                    if(alias.getText().toString().isEmpty()){
                        errorAlias.setText(R.string.campoObligatorio);
                        errorAlias.setVisibility(View.VISIBLE);
                        validar=false;
                    }
                    if(CBUVendedor.getText().toString().isEmpty()){
                        errorCBU.setText(R.string.campoObligatorio);
                        errorCBU.setVisibility(View.VISIBLE);
                        validar=false;
                    }
                }
                ////////////////////////////////////////////
                // VALIDACION DE CONTRASEÑAS COINCIDENTES //
                ////////////////////////////////////////////
                if(!password.getText().toString().equals(passwordR.getText().toString())){
                    errorPassR.setText(R.string.contraseñasNoCoinciden);
                    errorPassR.setVisibility(View.VISIBLE);
                    validar=false;
                }
                ///////////////////////////
                // VALIDACION DEL CORREO //
                ///////////////////////////
                if(!Patterns.EMAIL_ADDRESS.matcher(correo.getText()).matches()){
                    errorCorreo.setText(R.string.correoIncorrecto);
                    errorCorreo.setVisibility(View.VISIBLE);
                    validar=false;
                }
                ////////////////////////////////////////
                // VALIDACIONES DE TARJETA DE CREDITO //
                ////////////////////////////////////////
                if(!tarjetaNumero.getText().toString().isEmpty()){
                    if(tarjetaNumero.getText().toString().length()!=19){
                        errorTarjetaNumero.setText(R.string.longitudIncorrecta);
                        errorTarjetaNumero.setVisibility(View.VISIBLE);
                        validar=false;
                    }
                    if(tarjetaCCV.getText().toString().length()!=3){
                        errorCCV.setText(R.string.longitudIncorrecta);
                        errorCCV.setVisibility(View.VISIBLE);
                        validar=false;
                    }

                    if(tarjetaVencimiento.getText().toString().length()!=5){
                        errorTarjetaVencimiento.setText(R.string.longitudIncorrecta);
                        errorTarjetaVencimiento.setVisibility(View.VISIBLE);
                        validar=false;
                    }

                }
                if(tarjetaCCV.getText().toString().isEmpty()){
                    errorCCV.setText(R.string.campoObligatorio);
                    errorCCV.setVisibility(View.VISIBLE);
                    validar=false;
                }
                if(tarjetaVencimiento.getText().toString().isEmpty()) {
                    errorTarjetaVencimiento.setText(R.string.campoObligatorio);
                    errorTarjetaVencimiento.setVisibility(View.VISIBLE);
                    validar=false;
                }else{

                    int mes = Integer.parseInt(tarjetaVencimiento.getText().toString().substring(0,2));
                    int anio = Integer.parseInt(tarjetaVencimiento.getText().toString().substring(3,5));

                    LocalDate today = LocalDate.now();

                    //convierto las dos fecha a meses y resto
                    int diferenciaMeses = (anio*12 + mes) - ((today.getYear()%100)*12 + (today.getMonthValue()));

                    if(mes>12 || mes<1 || (today.getYear()%100 > anio) || diferenciaMeses<3){
                        errorTarjetaVencimiento.setText(R.string.errorTarjetaVencimiento);
                        errorTarjetaVencimiento.setVisibility(View.VISIBLE);
                        validar=false;
                    }

               }
                if(validar) {
                    ///////////////////////////
                    // DAR DE ALTA LA CUENTA //
                    ///////////////////////////
                    Toast.makeText(context, R.string.toastRegistroExitoso, Toast.LENGTH_SHORT).show();
                }
                    else {
                    Toast.makeText(context, R.string.toastCamposObligatorios, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //cierre del onCreate
    }
}