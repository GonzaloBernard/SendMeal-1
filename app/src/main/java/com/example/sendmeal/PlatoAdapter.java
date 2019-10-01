package com.example.sendmeal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.sendmeal.domain.Plato;
import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoHolder> {

    private static final int REQUEST_CODE_EDITAR_PLATO = 2;
    private List<Plato> mDataset;
    public PlatoAdapter(List<Plato> myDataset) {
        mDataset = myDataset;

    }



    @Override
    public PlatoAdapter.PlatoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent, false);
        PlatoHolder vh = new PlatoHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onBindViewHolder(PlatoHolder holder, int position) {
        final Plato plato = mDataset.get(position);
        holder.imagen.setImageResource(plato.getImagen());
        holder.titulo.setText(plato.getTitulo());
        holder.precio.setText(plato.getPrecio().toString());

        holder.buttonEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(view.getContext(),EditarPlato.class);
                i1.putExtra("idPlato",plato.getId());
                i1.putExtra("titulo",plato.getTitulo());
                i1.putExtra("descripcion",plato.getDescripcion());
                i1.putExtra("precio",plato.getPrecio());
                i1.putExtra("calorias",plato.getCalorias());
                ((Activity) view.getContext()).startActivityForResult(i1, REQUEST_CODE_EDITAR_PLATO);
            }
        });
    }
    public class PlatoHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView titulo;
        TextView precio;
        Button buttonEditar;
        public PlatoHolder(View base){
            super(base);
            this.imagen = (ImageView) base.findViewById(R.id.filaPlatoImageView);
            this.titulo = (TextView) base.findViewById(R.id.filaPlatoTextViewTitulo);
            this.precio = (TextView) base.findViewById(R.id.filaPlatoTextViewPrecio);
            this.buttonEditar = (Button) base.findViewById(R.id.buttonEditar);
        }

    }

}