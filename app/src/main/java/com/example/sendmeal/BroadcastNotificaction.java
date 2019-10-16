package com.example.sendmeal;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BroadcastNotificaction  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_items);
        BroadcastReceiver br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_EN_OFERTA);
        getApplication().getApplicationContext()
                .registerReceiver(br, filtro);
    }
}
