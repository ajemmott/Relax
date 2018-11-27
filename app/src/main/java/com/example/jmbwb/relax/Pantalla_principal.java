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
import android.widget.Toast;


public class Pantalla_principal extends AppCompatActivity {
    private ActionBar toolbar;
    DatabaseHelper db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //dependiendo de donde se selecciona, se abre un fragmento diferente
            Fragment fragmento;
            Intent obtenerNombre = getIntent();
            String correo = obtenerNombre.getStringExtra("correo_user");
            Bundle b = new Bundle();
            b.putString("correo_user", correo);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Inicio"); //Texto de la franja de arriba
                    //creando el fragmento de contacto
                    fragmento = new Fragmento_inicio();
                    //cargando
                    cargarFragmento(fragmento, b);
                    return true;
                case R.id.favoritos:
                    toolbar.setTitle("Favoritos");
                    //creando el fragmento de contacto
                    fragmento = new Fragmento_favorito();
                    //cargando
                    cargarFragmento(fragmento, b);
                    return true;
                case R.id.perfil:
                    toolbar.setTitle("Mi perfil");
                    //creando el fragmento de perfil de usuario
                    fragmento = new Fragmento_perfil();

                    //cargando
                    cargarFragmento(fragmento, b);
                    return true;
                case R.id.contacto:
                    toolbar.setTitle("Contactenos");
                    //creando el fragmento de contacto
                    fragmento = new Fragmento_contacto();
                    //cargando
                    cargarFragmento(fragmento, b);
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
        toolbar.setTitle("Técnicas"); //por default

        //por default cargar el fragmento de inicio
        Intent obtenerNombre = getIntent();
        String correo = obtenerNombre.getStringExtra("correo_user");
        Bundle b = new Bundle();
        b.putString("correo_user", correo);
        cargarFragmento(new Fragmento_inicio(),b);
    }

    public void cargarFragmento(Fragment fragmento, Bundle b){
        fragmento.setArguments(b);
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        transaccion.replace(R.id.fragmentHolder, fragmento);
        transaccion.addToBackStack(null);
        transaccion.commit();
    }

}
