package com.example.sendmeal;

import android.app.PendingIntent;
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
        Toast.makeText(context, "mensaje recibido", Toast.LENGTH_LONG).show();

        Intent destino = new Intent(context, CrearItem.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, ListaItems.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(intent.getExtras().getString("Titulo"))
                        .setContentText(intent.getExtras().getString("Descripcion"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(99, mBuilder.build());

    }
}
