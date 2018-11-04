package com.example.jmbwb.relax;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jmbwb.relax.R;

import java.util.ArrayList;

public class Fragmento_inicio extends Fragment {
    View view;
    public static ArrayList <DatosTecnicas> datosTecnicas = new ArrayList<>();     //datos del recycler view

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //definir el xml para el fragmento
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        RecyclerView rv_tecnicas = view.findViewById(R.id.rv_tecnicas);

        rv_tecnicas.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        datosTecnicas.add(new DatosTecnicas("Caminar", "9YfnG_UC5oU", "Camina. No importa si es de camino a la oficina, del supermercado al colegio de los niños, de la estación de tren a casa, o simplemente mientras andas a la orilla del mar o entre los árboles.",R.drawable.t_caminar, 0));
        datosTecnicas.add(new DatosTecnicas("Conversar", "FhMA9FKFwjc", "", R.drawable.t_conversar, 0));
        datosTecnicas.add(new DatosTecnicas("Correr", "zq0ua8CPMeo", "Al correr nuestro organismo continua experimentando beneficios una vez en reposo: se generan endorfinas que son opiáceos endógenos que provocan sensaciones placenteras y de bienestar, minimizando estados de ansiedad y aumentando la relajación.", R.drawable.t_correr,0));
        datosTecnicas.add(new DatosTecnicas("Escuchar música","4FvuEaByqLE", "La música es una herramienta muy poderosa ya que tiene el poder de transmitir emociones, y está comprobado que la música que escuchamos afecta al ser humano de  diferentes formas y llega a modificar el estado de ánimo. " ,R.drawable.t_escucharmusica,0));
        datosTecnicas.add(new DatosTecnicas("Relajación Progresiva","eu-2iWv_fCM" ,"Consiste en ir relajando los grupos musculares del cuerpo de manera progresiva, como si se tratase del recorrido de un pequeño tren imaginario. Es un ejercicio muy útil para reducir la ansiedad relacionada con procesos físicos como el deporte o el seguimiento de horarios estrictos. Sin embargo, hacerlo te llevará más rato que el resto, por lo que deberías de asegurarte que dispones de un tiempo en el que nadie te va a molestar.",R.drawable.t_jacobson, 0));
        datosTecnicas.add(new DatosTecnicas("Jugar con tu mascota", "zkJGccBiCkg", "El efecto calmante de una mascota también protege contra la soledad, depresión y ansiedad. Los psicoterapeutas reportan que muchos pacientes tensos y ansiosos se calman y relajan cuando se lleva un animal a la habitación en la que se encuentran.", R.drawable.t_jugarmascota,0));
        datosTecnicas.add(new DatosTecnicas("Meditar", "a9fBwDihz5c","Para esta técnica de relajación necesitarás sentarte en una silla cómoda y empezar a seguir los pasos descritos en el ejercicio de respiración con el diafragma. ",R.drawable.t_meditar,0));
        datosTecnicas.add(new DatosTecnicas("Respirar", "Df5cnoSVz-U", "La respiración profunda ayuda a activar el sistema nervioso parasimpático, induciendo a la relajación. La técnica de respiración denominada 4, 7, 8 del Dr. Weil funciona como un tranquilizante natural para el sistema nervioso.", R.drawable.t_respirar,0));
        datosTecnicas.add(new DatosTecnicas("Tomar Té", "EnSash8H_Ac", "Existen algunas infusiones que nos ayudan a aliviar tensiones y nos pueden resultar de utilidad en estas situaciones.", R.drawable.t_tomarte, 0));
        datosTecnicas.add(new DatosTecnicas("Yoga", "1J8CRcoFekE", "El yoga ha demostrado ser particularmente beneficioso si se sufre de dolor de espalda, y también puede ser de gran beneficio para la salud mental.", R.drawable.t_yoga,0));

        //creando adaptador
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(datosTecnicas);
        //asignandolo
        rv_tecnicas.setAdapter(adapter);
        //animador
        rv_tecnicas.setItemAnimator(new DefaultItemAnimator());
        //estableciendo como un grid de dos columnas
        rv_tecnicas.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //para la linea que separa cada uno
        rv_tecnicas.addItemDecoration(new DividerItemDecoration(rv_tecnicas.getContext(), DividerItemDecoration.VERTICAL));
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
