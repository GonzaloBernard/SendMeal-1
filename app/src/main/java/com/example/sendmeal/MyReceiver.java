package com.example.sendmeal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Se ha pulsado el botón.", Toast.LENGTH_SHORT)
                .show();
    }
}
