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
            //Compara si el token recibido en el mensaje push coincide con el token del usuario
            if(entrada.getKey().equals("token") && entrada.getValue().equals(getTokenFromPrefs()) ){
                // EL MENSAJE RECIBIDO ES PARA ESTE USUARIO en particular
                Log.d(TAG,"Mensaje personal recibido");
            }
        }
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
