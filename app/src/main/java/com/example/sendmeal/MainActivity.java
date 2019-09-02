package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Declaracion de views
    private SeekBar seekBar;
    private TextView textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////////////////////////////////////////////////////////////
        // 02/09 GONZA LOGICA DEL SEEKBAR                                    //
        ///////////////////////////////////////////////////////////////////////
        //Inicializacion de views
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView7 = (TextView) findViewById(R.id.textView7);

        //Muestra el valor del seekbar en un textview (textView7)
        textView7.setText("100");
        seekBar.setOnSeekBarChangeListener(new
        SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            progress = progress / 100;
            progress = progress * 100;
            textView7.setText(progress + 100 + "");
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
         {}
         });
        //////////////////////////////////////////////////////////////////////////
        //                                                                      //
        //////////////////////////////////////////////////////////////////////////
    }
}

