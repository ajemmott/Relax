package com.example.jmbwb.relax;

import android.content.Context;
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
    //private DatosTecnicas[] datosTec;
    private ArrayList<String> datosTitulo;
    private ArrayList<String> datosURL;
    private ArrayList<String> datosdescrip;
    private ArrayList<Integer> datosimagen;

    public RecyclerViewAdapter(ArrayList<String> datosTitulo, ArrayList<String> datosURL, ArrayList<String> datosdescrip, ArrayList<Integer> datosimagen){
        this.datosTitulo = datosTitulo;
        this.datosURL = datosURL;
        this.datosdescrip = datosdescrip;
        this.datosimagen = datosimagen;
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
        viewHolder.textview.setText(datosTitulo.get(i));
        viewHolder.imageView.setImageResource(datosimagen.get(i));

        //Para cuando se selecciona un item del recycler view
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abriendo la Actividad para ver la tecnica a detalle
                Intent intent = new Intent(view.getContext(), MostrartecnicaActivity.class);
                //pasando datos de la tecnica
                intent.putExtra("url_video", datosURL.get(i));
                intent.putExtra("descripcion", datosdescrip.get(i));
                //iniciando actividad
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datosTitulo.size();
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
