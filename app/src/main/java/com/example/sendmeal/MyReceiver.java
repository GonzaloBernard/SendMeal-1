package com.example.sendmeal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {

    public static final String EVENTO_EN_OFERTA = "android.intent.action.EVENTO";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, ListaItems.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(intent.getExtras().getString("data1"))
                        .setContentText(intent.getExtras().getString("data2"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(99, mBuilder.build());
    }
}
