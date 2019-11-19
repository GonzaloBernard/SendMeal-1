package com.example.sendmeal;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private final String TAG = "APP_MSG";
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Map<String,String> datas = remoteMessage.getData();
        for(Map.Entry<String,String> entrada : datas.entrySet()){
            Log.d(TAG, "Detalle dato ( : " + entrada.getKey() +" _ "+ entrada.getValue() );

            //Compara si el token recibido en el mensaje push coincide con el token del usuario
            if(entrada.getKey().equals("token") && entrada.getValue().equals(getTokenFromPrefs()) ){
                // EL MENSAJE RECIBIDO ES PARA ESTE USUARIO en particular
                Log.d(TAG,"Mensaje personal recibido");
            }
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob("dato1");
            } else {
                // Handle message within 10 seconds
                handleNow("dato1");
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void scheduleJob(String token){
        Log.d(TAG, "ENVIAR token: " + token);
    }


    private void handleNow(String token){
        Log.d(TAG, "ENVIAR token: " + token);
    }


    private void sendRegistrationToServer(String token){
        Log.d(TAG, "ENVIAR token: " + token);
    }

    private String getTokenFromPrefs(){
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }

}
