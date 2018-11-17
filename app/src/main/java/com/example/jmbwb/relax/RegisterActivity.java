package com.example.jmbwb.relax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_registrar = findViewById(R.id.btn_registrar);
        et_nombre = findViewById(R.id.et_nombre);
        et_edad = findViewById(R.id.et_edad);
        et_correo = findViewById(R.id.et_correo);
        et_password = findViewById(R.id.et_password);
        rb_mujer = findViewById(R.id.rb_mujer);
        rb_hombre = findViewById(R.id.rb_hombre);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios");

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "Espere un segundo", Toast.LENGTH_LONG).show();
                final String nombre = et_nombre.getText().toString();
                final String edad = et_edad.getText().toString();
                final String correo = et_correo.getText().toString();
                final String contraseña = et_password.getText().toString();
                //final char genero;
                if (rb_hombre.isSelected()) {
                    final char genero = 'H';
                } else{
                    final char genero = 'M';
                }
                if (!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contraseña) && !TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(edad)) {
                    mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = mDatabase.child(user_id);
                            current_user_db.child("Usuario").setValue(nombre);
                            current_user_db.child("Edad").setValue(edad);
                            current_user_db.child("Género").setValue(genero);
                            Toast.makeText(RegisterActivity.this, "Registro anotado", Toast.LENGTH_SHORT).show();
                            Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            regIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(regIntent);
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
