package com.example.jmbwb.relax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button btn_registrar;
    EditText et_nombre, et_edad, et_correo, et_password;
    RadioButton rb_mujer, rb_hombre;
    RadioGroup rg_genero;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final DatabaseHelper db = new DatabaseHelper(RegisterActivity.this);

        constraintLayout = findViewById(R.id.constraintLayout);
        btn_registrar = findViewById(R.id.btn_registrar);
        et_nombre = findViewById(R.id.et_nombre);
        et_edad = findViewById(R.id.et_edad);
        et_correo = findViewById(R.id.et_correo);
        et_password = findViewById(R.id.et_password);
        rb_mujer = findViewById(R.id.rb_mujer);
        rb_hombre = findViewById(R.id.rb_hombre);
        rg_genero = findViewById(R.id.rg_genero);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Busco id del radioButton seleccionado en el radioGroup
                    int rb_id = rg_genero.getCheckedRadioButtonId();
                    //rb_genero es el seleccionado
                    RadioButton rb_genero = rg_genero.findViewById(rb_id);

                    //Guardando los campos llenados
                    final String nombre = et_nombre.getText().toString();
                    final int edad = Integer.parseInt(et_edad.getText().toString());
                    final String correo = et_correo.getText().toString();
                    final String contraseña = et_password.getText().toString();
                    final String genero = rb_genero.getText().toString(); //Obteniendo texto del radioButton seleccionado

                    if (!TextUtils.isEmpty(nombre) || !TextUtils.isEmpty(correo) || !TextUtils.isEmpty(contraseña) || !TextUtils.isEmpty(genero) || et_edad.getText().toString().length() > 0) {
                        Usuarios usuario = new Usuarios();

                        if (!db.validarUsuario(correo)) {
                            usuario.setNombre(nombre);
                            usuario.setContraseña(contraseña);
                            usuario.setCorreo(correo);
                            usuario.setEdad(edad);
                            usuario.setGenero(genero);
                            usuario.setTipo(0);
                            db.crearUsuario(usuario);
                            Toast.makeText(RegisterActivity.this,"Registrado con éxito", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Snackbar.make(constraintLayout, "Su correo ya está registrado", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Snackbar.make(constraintLayout, "Llene todos los campos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}

