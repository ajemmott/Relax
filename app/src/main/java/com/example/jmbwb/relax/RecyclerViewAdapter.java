package com.example.jmbwb.relax;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private DatosTecnicas[] datosTec;

    public RecyclerViewAdapter(DatosTecnicas[] datosTec){
        this.datosTec=datosTec;
    }

    //Creando nuevas vistas
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_cuadritos, null);
        return new ViewHolder(view);
    }

    //Metodo que reemplaza los contenidos de la vista
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textview.setText(datosTec[i].getTitulo());
        viewHolder.imageView.setImageResource(datosTec[i].getImagen());
    }

    @Override
    public int getItemCount() {
        return datosTec.length;
    }

    //clase encargada de tener las referencias de cada item del RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textview;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.textview);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
