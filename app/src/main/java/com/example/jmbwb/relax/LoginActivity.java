package com.example.jmbwb.relax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();

        Button btn_empezar = findViewById(R.id.button_empezar);
        final EditText et_nombre = findViewById(R.id.editText_name);
        final EditText et_edad = findViewById(R.id.editText_edad);

        btn_empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_edad.length() != 0 && et_nombre.length() != 0){
                    int edad= Integer.parseInt(et_edad.getText().toString());
                    if (edad > 0 && edad <=110){
                        Intent submitUserData = new Intent(getApplicationContext(),Pantalla_principal.class);
                        submitUserData.putExtra("nombreUsuario",String.valueOf(et_nombre.getText().toString()));
                        submitUserData.putExtra("edadUsuario",Integer.parseInt(String.valueOf(et_edad.getText().toString())));
                        startActivity(submitUserData);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Ingrese una edad válida",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Debes introducir los campos solicitados",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
