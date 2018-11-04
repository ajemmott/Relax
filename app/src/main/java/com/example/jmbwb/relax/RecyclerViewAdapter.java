package com.example.jmbwb.relax;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<DatosTecnicas> datosTecnicas;

    public RecyclerViewAdapter(ArrayList<DatosTecnicas> datosTecnicas){
        this.datosTecnicas = datosTecnicas;
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
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        //viewHolder.textview.setText(datosTec[i].getTitulo());
        viewHolder.textview.setText(datosTecnicas.get(i).getTitulo());
        viewHolder.imageView.setImageResource(datosTecnicas.get(i).getImagen());

        //Para cuando se selecciona un item del recycler view
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abriendo la Actividad para ver la tecnica a detalle
                Intent intent = new Intent(view.getContext(), MostrartecnicaActivity.class);
                //pasando datos de la tecnica
                intent.putExtra("nombre", datosTecnicas.get(i).getTitulo());
                intent.putExtra("url_video", datosTecnicas.get(i).getUrl_video());
                intent.putExtra("descripcion", datosTecnicas.get(i).getDescripcion());

                //iniciando actividad
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datosTecnicas.size();
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
