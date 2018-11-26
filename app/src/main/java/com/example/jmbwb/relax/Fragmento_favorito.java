package com.example.jmbwb.relax;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Fragmento_favorito extends Fragment {
    View view;
    public ArrayList<DatosTecnicas> favoritos = new ArrayList<>();     //Lista de favoritos
    TextView tv_vacio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //definir el xml para el fragmento
        View view = inflater.inflate(R.layout.fragmento_favorito, container, false);

        tv_vacio = view.findViewById(R.id.tv_vacio);

        //El recycler view
        RecyclerView rv_favoritos = view.findViewById(R.id.rv_favoritos);
        //El layout manager
        rv_favoritos.setLayoutManager(new LinearLayoutManager(getActivity()));

        int j=0;
        for (int i=0; i < Fragmento_inicio.datosTecnicas.size(); i++){
            //Float actual = Fragmento_inicio.datosTecnicas.get(i).getRating();
            //if (actual > 3.5){
             //   favoritos.add(j, Fragmento_inicio.datosTecnicas.get(i));
             //   j++;
            //}
        }

        if (favoritos.isEmpty()) {
            rv_favoritos.setVisibility(View.GONE);
            tv_vacio.setVisibility(View.VISIBLE);
        }
        else {
            rv_favoritos.setVisibility(View.VISIBLE);
            tv_vacio.setVisibility(View.GONE);
        }

        //creando adaptador
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(favoritos, "");
        //asignandolo
        rv_favoritos.setAdapter(adapter);
        //animador
        rv_favoritos.setItemAnimator(new DefaultItemAnimator());
        //estableciendo como un grid de dos columnas
        rv_favoritos.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //para la linea que separa cada uno
        rv_favoritos.addItemDecoration(new DividerItemDecoration(rv_favoritos.getContext(), DividerItemDecoration.VERTICAL));

        //definir el xml para el fragmento
        return view;
    }

    //Cualquier setup de las vistas es acá
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
