package com.example.sendmeal;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.sendmeal.dao.PedidoRepository;
import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.Pedido;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private final String TAG = "APP_MSG";
    private EstadoPedido ESTADO_PEDIDO;
    private String ID_PEDIDO;
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
        for(Map.Entry<String,String> entrada : datas.entrySet()) {
            if(entrada.getKey().equals("ID_PEDIDO")){
                ID_PEDIDO=entrada.getValue();
            }
            if(entrada.getKey().equals("ESTADO")){
                ESTADO_PEDIDO=EstadoPedido.valueOf(entrada.getValue());
            }

            //Compara si el token recibido en el mensaje push coincide con el token del usuario
            if (entrada.getKey().equals("TOKEN") && entrada.getValue().equals(getTokenFromPrefs())) {
                // EL MENSAJE RECIBIDO ES PARA ESTE USUARIO en particular
                Log.d(TAG, "Mensaje personal recibido");
                //PROBANDO ACTUALIZAR ESTADO DE PEDIDO MEDIANTE NOTIFICACIONES PUSH
                // SE PIDE UNA INSTANCIA DEL REPO
                PedidoRepository pedidoRepository = PedidoRepository.getInstance(this);
                pedidoRepository.listarPedidos(miHandler);
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


    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {

            // SE PIDE UNA INSTANCIA DEL REPO
            PedidoRepository pedidoRepository = PedidoRepository.getInstance(FirebaseMessagingService.this);
            List<Pedido> listaPedidos = pedidoRepository.getListaPedidos();
            switch (msg.arg1 ) {
                case PedidoRepository._CONSULTA_PEDIDO:

                    for(Pedido pedido : listaPedidos){
                        if(pedido.getId()>0 && pedido.getId()==Integer.parseInt(ID_PEDIDO)){
                            pedido.setEstado(ESTADO_PEDIDO);
                            // SE ENVIA AL API REST
                            pedidoRepository.actualizarPedidoREST(pedido, miHandler);
                        }
                    }
                    break;
                case PedidoRepository._MODIFICACION_PEDIDO_REST:
                    break;
                case PedidoRepository._ERROR_PEDIDO_REST:
                    break;
                default:break;
            }
        }
    };

}
