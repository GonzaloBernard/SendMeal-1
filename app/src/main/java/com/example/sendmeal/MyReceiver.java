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

    //ES UN ID AUTOINCREMENTAL PARA LAS NOTIFICACIONES
    private Integer idNotificacion=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "mensaje recibido", Toast.LENGTH_LONG).show();
        //Se crea el intent para mostrar el Plato
        Intent destino = new Intent(context, ABMPlato.class);
        //SE AGREGA EL PLATO A MODIFICAR
        destino.putExtra("plato",intent.getSerializableExtra("plato"));
        //SE AGREGA LA ACCION REQUERIDA EN ESTE CASO EL MODO CONSULTA
        destino.putExtra("modo",2);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //SE LANZA EL INTENT
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, 0);
        // LOGIA DE LA NOTIFICACION

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, ListaItems.CHANNEL_ID)
                        .setSmallIcon(R.drawable.hamburguesa)
                        .setContentTitle(intent.getExtras().getString("Titulo"))
                        .setContentText(intent.getExtras().getString("Descripcion"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(idNotificacion++, mBuilder.build());

    }
}
