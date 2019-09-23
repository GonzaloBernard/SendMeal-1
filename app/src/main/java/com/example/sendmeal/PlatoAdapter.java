package com.example.sendmeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sendmeal.domain.Plato;
import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoHolder> {

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
        Plato plato = mDataset.get(position);
        holder.imagen.setImageResource(R.drawable.ic_launcher_foreground);
        holder.titulo.setText(plato.getTítulo());
        holder.precio.setText(plato.getPrecio().toString());

    }

    public class PlatoHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView titulo;
        TextView precio;
        public PlatoHolder(View base){
            super(base);
            this.imagen = (ImageView) base.findViewById(R.id.filaPlatoImageView);
            this.titulo = (TextView) base.findViewById(R.id.filaPlatoTextViewTitulo);
            this.precio = (TextView) base.findViewById(R.id.filaPlatoTextViewPrecio);
        }
    }

}