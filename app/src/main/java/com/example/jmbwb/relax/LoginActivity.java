package com.example.jmbwb.relax;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_empezar = findViewById(R.id.button_empezar);
        final EditText et_correo = findViewById(R.id.editText_correo);
        final EditText et_contra = findViewById(R.id.editText_contraseña);
        constraintLayout = findViewById(R.id.constraintLayout);

        btn_empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
                if (et_correo.length() != 0 && et_contra.length() != 0){
                    String correo = et_correo.getText().toString();
                    String contraseña = et_contra.getText().toString();

                    if (db.validarUsuario(correo, contraseña)){
                        //Pasando el correo del usuario
                        Intent intent = new Intent(LoginActivity.this, Pantalla_principal.class);
                        intent.putExtra("correo_user",correo);
                        startActivity(intent);
                        finish();
                    }else{
                        Snackbar.make(constraintLayout,"Información errónea", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(constraintLayout,"Revise los campos solicitados", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onBackPressed() {
        // Se queda vacio para que el usuario que cerró sesión no vuelva a entrar
        //Nada sucede
    }
}