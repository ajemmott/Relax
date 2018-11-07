package com.example.jmbwb.relax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class Pantalla_principal extends AppCompatActivity {
    private ActionBar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //dependiendo de donde se selecciona, se abre un fragmento diferente
            Fragment fragmento;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Inicio"); //Texto de la franja de arriba
                    //creando el fragmento de contacto
                    fragmento = new Fragmento_inicio();
                    //cargando
                    cargarFragmento(fragmento);
                    return true;
                case R.id.favoritos:
                    toolbar.setTitle("Favoritos");
                    //creando el fragmento de contacto
                    fragmento = new Fragmento_favorito();
                    //cargando
                    cargarFragmento(fragmento);
                    return true;
                case R.id.contacto:
                    toolbar.setTitle("Contactenos");
                    //creando el fragmento de contacto
                    fragmento = new Fragmento_contacto();
                    //cargando
                    cargarFragmento(fragmento);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        BottomNavigationView navigation = findViewById(R.id.b_navegacion);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //para cambiarle el nombre al toolbar al cambiar de item de navegacion
        toolbar = getSupportActionBar();
        toolbar.setTitle("Seleccione una Técnica"); //por default

        //por default cargar el fragmento de inicio
        cargarFragmento(new Fragmento_inicio());
    }

    public void cargarFragmento(Fragment fragmento){
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        transaccion.replace(R.id.fragmentHolder, fragmento);
        transaccion.addToBackStack(null);
        transaccion.commit();
    }

}
