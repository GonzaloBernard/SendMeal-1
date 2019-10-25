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
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sendmeal.domain.Plato;
import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoHolder> {

    private Context context;
    private static final int REQUEST_CODE_EDITAR_PLATO = 2;
    private List<Plato> mDataset;
    public PlatoAdapter(List<Plato> myDataset) {
        mDataset = myDataset;
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
        holder.imagen.setImageResource(plato.getImagen());
        holder.titulo.setText(plato.getTitulo());
        holder.precio.setText(plato.getPrecio().toString());
        ///////////////////////////////
        //CLICK EN BOTON EDITAR////////
        ///////////////////////////////
        holder.buttonEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ABMPlato.class);
                //SE AGREGA EL PLATO
                i.putExtra("plato", plato);
                //EL MODO DETERMINA LA ACCION A REALIZAR( CREAR=1 EDITAR=2 CONSULTAR=3 )
                i.putExtra("modo", 2);
                ((Activity) view.getContext()).startActivityForResult(i, REQUEST_CODE_EDITAR_PLATO);
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
                        intent.putExtra("plato",plato);
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
                                        mDataset.remove(plato);
                                        notifyDataSetChanged();
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

        public PlatoHolder(View base){
            super(base);
            this.imagen = (ImageView) base.findViewById(R.id.filaPlatoImageView);
            this.titulo = (TextView) base.findViewById(R.id.filaPlatoTextViewTitulo);
            this.precio = (TextView) base.findViewById(R.id.filaPlatoTextViewPrecio);
            this.buttonEditar = (Button) base.findViewById(R.id.buttonEditar);
            this.buttonEliminar = (Button) base.findViewById(R.id.buttonEliminar);
            this.buttonOferta = (Button) base.findViewById(R.id.buttonOferta);
        }

    }

}