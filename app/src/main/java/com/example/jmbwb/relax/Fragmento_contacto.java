package com.example.jmbwb.relax;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jmbwb.relax.R;

public class Fragmento_contacto extends Fragment {
    View view;
    Button btn_llamada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmento_contactenos, container, false);

        // Referencia al botón
        btn_llamada = view.findViewById(R.id.btn_llamada);

        btn_llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //avisando al usuario que se va a abrir el dialer
                Toast.makeText(getActivity(), "Abriendo dialer", Toast.LENGTH_LONG).show();

                //Intent para abrir el dialer
                Intent intent = new Intent(Intent.ACTION_DIAL);
                //el prefijo tel es requerido
                intent.setData(Uri.parse("tel:3444282"));
                startActivity(intent);
            }
        });
        return view;
    }
}
