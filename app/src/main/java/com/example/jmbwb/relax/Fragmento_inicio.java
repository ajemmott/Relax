package com.example.jmbwb.relax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jmbwb.relax.R;

public class Fragmento_inicio extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //definir el xml para el fragmento
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        RecyclerView rv_tecnicas = view.findViewById(R.id.rv_tecnicas);

        rv_tecnicas.setLayoutManager(new LinearLayoutManager(getActivity()));

        //datos del recycler view
        DatosTecnicas datos[] = {
                new DatosTecnicas("Caminar", R.drawable.t_caminar),
                new DatosTecnicas("Conversar", R.drawable.t_conversar),
                new DatosTecnicas("Correr", R.drawable.t_correr),
                new DatosTecnicas("Escuchar música", R.drawable.t_escucharmusica),
                new DatosTecnicas("Relajación Progresiva", R.drawable.t_jacobson),
                new DatosTecnicas("Jugar con tu mascota", R.drawable.t_jugarmascota),
                new DatosTecnicas("Meditar", R.drawable.t_meditar),
                new DatosTecnicas("Respirar", R.drawable.t_respirar),
                new DatosTecnicas("Tomar Té", R.drawable.t_tomarte),
                new DatosTecnicas("Yoga", R.drawable.t_yoga)
        };

        //creando adaptador
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(datos);
        //asignandolo
        rv_tecnicas.setAdapter(adapter);
        //animador
        rv_tecnicas.setItemAnimator(new DefaultItemAnimator());
        //estableciendo como un grid de dos columnas
        rv_tecnicas.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return view;
    }

    //Cualquier setup de las vistas es acá
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv_nuevas = view.findViewById(R.id.tv_nuevas);
        RecyclerView rv_nuevas = view.findViewById(R.id.rv_nuevas);
        TextView tv_tecnicas = view.findViewById(R.id.tv_tecnicas);
        RecyclerView rv_tecnicas = view.findViewById(R.id.rv_tecnicas);
    }
}
