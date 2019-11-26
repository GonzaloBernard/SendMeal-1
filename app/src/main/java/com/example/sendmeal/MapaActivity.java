package com.example.sendmeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sendmeal.dao.PedidoRepository;
import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.Pedido;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



import java.util.List;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private List<Pedido> listaDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMapaActivity);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.tituloToolbarUbicacion);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        final Button buttonUbicacion;
        final Spinner spinnerEstadosPedido;
        buttonUbicacion = (Button) findViewById(R.id.buttonOkUbicacion);
        spinnerEstadosPedido = (Spinner) findViewById(R.id.spinnerEstadoPedido);

        PedidoRepository.getInstance(MapaActivity.this).listarPedidos(miHandler);

        buttonUbicacion.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.spinnerEstadosPedidos,
                android.R.layout.simple_spinner_item);

        spinnerEstadosPedido.setAdapter(adapterSpinner);
        spinnerEstadosPedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    mostrarPedidosEnMapa(parent.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        actualizarMapa();
    }

    private void actualizarMapa() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(marker == null ) {
                    marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .draggable(true)
                            .alpha(0.7f)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                }else{
                    marker.setPosition(latLng);
                }
                AltaPedido.latitud = latLng.latitude;
                AltaPedido.longitud = latLng.longitude;
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hashCapture){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 9999: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    actualizarMapa();
                }
            }
        }
    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {

             listaDataSet = PedidoRepository.getInstance(MapaActivity.this).getListaPedidos();
        }
    };


    private void mostrarPedidosEnMapa(String estado) {

        mMap.clear();

        switch (estado){
            case "Todos":
             //   for (Pedido p: listaDataSet) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(-31.6165504,-60.67549)) //PARA PROBAR NOMAS
                            .alpha(0.7f)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
               // }
                break;
            case "Pendiente":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.PENDIENTE)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "Enviado":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.ENVIADO)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "Aceptado":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.ACEPTADO)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "Rechado":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.RECHAZADO)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "En preparacion":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.EN_PREPARACION)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "En envio":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.EN_ENVIO)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "Entregado":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.ENTREGADO)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "Cancelado":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.CANCELADO)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
                default:
                    Toast.makeText(this,estado,Toast.LENGTH_LONG).show();

        }

    }/*else {
            estadoSeleccionado = EstadoPedido.valueOf(listaEstados[posicionSeleccionada]);
            for (Pedido p: pedidos) {
                if (p.getEstado().equals(estadoSeleccionado)) {
                    addMarker(p);
                    if(estadoSeleccionado.equals(EstadoPedido.EN_ENVIO)){
                        lineaPedidoEnEnvioOpt.add(new LatLng(p.getLatitud(), p.getLongitud()));
                    }
                }
            }

            map.addPolyline(lineaPedidoEnEnvioOpt);

        }*/

}