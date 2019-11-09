package com.example.sendmeal;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sendmeal.domain.Plato;
import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoHolder> {

    private Context context;
    private List<Plato> mDataset;
    private String key_usuario;
    public List<Plato> platosPedido;
    public PlatoAdapter(List<Plato> myDataset, String key_usuario) {
        this.key_usuario = key_usuario;
        this.mDataset = myDataset;
    }

    @Override
    public PlatoAdapter.PlatoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent, false);
        PlatoHolder vh = new PlatoHolder(v);
        context=parent.getContext();
        return vh;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onBindViewHolder(final PlatoHolder holder, final int position) {
        final Plato plato = mDataset.get(position);
        holder.imagen.setImageResource(R.drawable.hamburguesa);
        holder.titulo.setText(plato.getTitulo());
        holder.precio.setText(plato.getPrecio().toString());

        switch (key_usuario)
        {
            case HomeActivity.KEY_VENDEDOR:
            holder.llVendedor.setVisibility(View.VISIBLE);
            holder.llComprador.setVisibility(View.GONE);
            break;
            case HomeActivity.KEY_COMPRADOR:
            holder.llVendedor.setVisibility(View.GONE);
            holder.llComprador.setVisibility(View.VISIBLE);
            break;
            default:
                break;
        }


        ///////////////////////////////
        //CLICK EN BOTON AGREGAR///////
        ///////////////////////////////
        holder.buttonAgregarCarrito.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                platosPedido.add(plato);

            }
        });


        ///////////////////////////////
        //CLICK EN BOTON EDITAR////////
        ///////////////////////////////
        holder.buttonEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AbmPlato.class);
                //EL MODO DETERMINA LA ACCION A REALIZAR
                i.putExtra(AbmPlato._PLATO_MODO_KEY, AbmPlato._KEY_EDITAR_PLATO);
                //SE AGREGA EL PLATO
                i.putExtra(AbmPlato._PLATO_INDIVIDUAL_KEY, plato);
                ((Activity) view.getContext()).startActivityForResult(i, ListaItems.REQUEST_CODE_EDITAR_PLATO);
            }
        });
        ///////////////////////////////
        //CLICK EN BOTON OFERTA////////
        ///////////////////////////////
        holder.buttonOferta.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                plato.setEnOferta(true);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.currentThread().sleep(5000);
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                         }
                        Intent intent = new Intent();
                        intent.putExtra(AbmPlato._PLATO_INDIVIDUAL_KEY, plato);
                        intent.putExtra("titulo",plato.getTitulo());
                        intent.putExtra("descripcion",plato.getDescripcion());
                        intent.setAction(MyReceiver.EVENTO_EN_OFERTA);
                        context.sendBroadcast(intent);
                    }
                };

                Thread t1 = new Thread(r);
                t1.start();
            }
        });
        ///////////////////////////////
        //CLICK EN BOTON ELIMINAR//////
        ///////////////////////////////
        holder.buttonEliminar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Quiere eliminar el plato?")
                        .setTitle("ELIMINAR PLATO")
                        .setPositiveButton("ELIMINAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        Intent intent = new Intent(context, AbmPlato.class);
                                        //EL MODO DETERMINA LA ACCION A REALIZAR
                                        intent.putExtra(AbmPlato._PLATO_MODO_KEY, AbmPlato._KEY_BORRAR_PLATO);
                                        //SE AGREGA EL PLATO
                                        intent.putExtra(AbmPlato._PLATO_INDIVIDUAL_KEY, plato);
                                        ((Activity) context).startActivityForResult(intent, ListaItems.REQUEST_CODE_BORRAR_PLATO);
                                    }
                                }).setNegativeButton("CONSERVAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlgInt, int i) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    public class PlatoHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView titulo;
        TextView precio;
        Button buttonEditar;
        Button buttonEliminar;
        Button buttonOferta;
        LinearLayout llVendedor;
        LinearLayout llComprador;
        Spinner spinnerCantidad;
        Button buttonAgregarCarrito;

        public PlatoHolder(View base){
            super(base);
            this.imagen = (ImageView) base.findViewById(R.id.filaPlatoImageView);
            this.titulo = (TextView) base.findViewById(R.id.filaPlatoTextViewTitulo);
            this.precio = (TextView) base.findViewById(R.id.filaPlatoTextViewPrecio);
            this.buttonEditar = (Button) base.findViewById(R.id.buttonEditar);
            this.buttonEliminar = (Button) base.findViewById(R.id.buttonEliminar);
            this.buttonOferta = (Button) base.findViewById(R.id.buttonOferta);
            this.llComprador = (LinearLayout) base.findViewById(R.id.layoutComprador);
            this.llVendedor = (LinearLayout) base.findViewById(R.id.layoutVendedor);
            this.spinnerCantidad = (Spinner) base.findViewById(R.id.spinnerCantidad);
            this.buttonAgregarCarrito = (Button) base.findViewById(R.id.buttonAgregarACarrito);
        }

    }

}