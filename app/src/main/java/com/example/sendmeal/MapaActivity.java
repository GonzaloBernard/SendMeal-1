package com.example.sendmeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


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
        buttonUbicacion = (Button) findViewById(R.id.buttonOkUbicacion);
        buttonUbicacion.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // SPINNER
        final Spinner spinnerEstadosPedido;
        spinnerEstadosPedido = (Spinner) findViewById(R.id.spinnerEstadoPedido);
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

        // SI EL INTENT SE GENERO DESDE HOMEACTIVITY SE DESHABILITA EL BOTON ENVIAR UBICACION
        // SI SE GENERA DESDE EL CARRITO DE COMPRA SE DESHABILITA EL SPINNER
        switch(getIntent().getStringExtra(HomeActivity.MAPA_KEY)){
            case HomeActivity.KEY_MAPA_ENVIOS:
                buttonUbicacion.setVisibility(View.INVISIBLE);
                break;
            case HomeActivity.KEY_MAPA_UBICACION:
                spinnerEstadosPedido.setVisibility(View.INVISIBLE);
        }

        PedidoRepository.getInstance(MapaActivity.this).listarPedidos(miHandler);
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

        // SI EL INTENT SE GENERO DESDE HOMEACTIVITY SE DESHABILITA LA OPCION DE OBTENER UBICACION
        if(HomeActivity.KEY_MAPA_UBICACION.equals(getIntent().getStringExtra(HomeActivity.MAPA_KEY))) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if (marker == null) {
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .draggable(true)
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    } else {
                        marker.setPosition(latLng);
                    }
                    AltaPedido.latitud = latLng.latitude;
                    AltaPedido.longitud = latLng.longitude;
                }
            });
        }
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
                for (Pedido p: listaDataSet) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(p.getLatitud(),p.getLongitud()))
                            .alpha(0.7f)
                            .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
                            .icon(BitmapDescriptorFactory.defaultMarker(getMarkerColorFByEstado(p.getEstado()))));
                }
                break;
            case "Pendiente":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.PENDIENTE)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
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
                                .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
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
                                .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "Rechazado":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.RECHAZADO)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
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
                                .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                break;
            case "En envio":
                PolylineOptions rectOptions = new PolylineOptions(); //CREO EL OBJETO POLYLANEOPTIONS
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.EN_ENVIO)) {

                        rectOptions.add(new LatLng(p.getLatitud(),p.getLongitud())).color(Color.RED);//SI ES EN_ENVIO LO AGREGO AL POLIGONO
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
                Polyline polyline = mMap.addPolyline(rectOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rectOptions.getPoints().get(0), 9));
                break;
            case "Entregado":
                for (Pedido p: listaDataSet) {
                    if (p.getEstado().equals(EstadoPedido.ENTREGADO)) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(p.getLatitud(), p.getLongitud()))
                                .alpha(0.7f)
                                .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
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
                                .title("id: "+p.getId()+ " - estado: "+p.getEstado()+ " - precio: 500")
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

    private float getMarkerColorFByEstado(EstadoPedido estado){
        float color = 0;
        switch (estado){
            case PENDIENTE:
                color = BitmapDescriptorFactory.HUE_CYAN;
                break;
            case ENVIADO:
                color = BitmapDescriptorFactory.HUE_GREEN;
                break;
            case ENTREGADO:
                color = BitmapDescriptorFactory.HUE_MAGENTA;
                break;
            case EN_PREPARACION:
                color = BitmapDescriptorFactory.HUE_ORANGE;
                break;
            case EN_ENVIO:
                color = BitmapDescriptorFactory.HUE_YELLOW;
                break;
            case RECHAZADO:
                color = BitmapDescriptorFactory.HUE_RED;
                break;
            case CANCELADO:
                color = BitmapDescriptorFactory.HUE_VIOLET;
                break;
        }

        return color;
    }

}