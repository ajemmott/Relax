package com.example.jmbwb.relax;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;

public class Fragmento_perfil extends Fragment{
    View view;
    EditText et_nombre, et_nedad, et_contraseña;
    TextView tv_Borrarcuenta;
    Button btn_guardar, btn_cerrar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //definir el xml para el fragmento
        View view = inflater.inflate(R.layout.fragmento_perfil, container, false);
        return view;
    }

    //Cualquier setup de las vistas es acá
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_nombre = view.findViewById(R.id.et_Nnombre);
        et_nedad = view.findViewById(R.id.et_Nedad);
        tv_Borrarcuenta = view.findViewById(R.id.tv_Borrarcuenta);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        btn_cerrar = view.findViewById(R.id.btn_cerrar);
        et_contraseña = view.findViewById(R.id.et_Npassword);

        //Para traer el id de usuario de Shared preference
        SharedPreferences preferencias = this.getContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        final int idUser = preferencias.getInt("id_user", 0);

        //traer de la base de datos y llenarlo en editText
        DatabaseHelper db = new DatabaseHelper(getContext());
        Usuarios usuario = db.obtenerInfo(idUser);
        et_nombre.setText(usuario.getNombre());
        et_nedad.setText(Integer.toString(usuario.getEdad()));
        et_contraseña.setText(usuario.getContraseña());

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nuevoNombre = et_nombre.getText().toString();
                String nuevaContra = et_contraseña.getText().toString();
                int nuevaEdad = Integer.parseInt(et_nedad.getText().toString());

                if (nuevoNombre.isEmpty()) {
                    et_nombre.setError("Introduzca un nombre");
                    et_nombre.requestFocus();
                    return;
                }
                if (nuevaEdad != 0  && nuevaEdad < 110) {
                    et_nedad.setError("Introduzca una edad válida");
                    et_nedad.requestFocus();
                    return;
                }
                if (nuevaContra.length() < 4){
                    et_contraseña.setError("Introduzca una contraseña de más caracteres");
                    et_contraseña.requestFocus();
                    return;
                }

                //poner lo que lo actualiza
                //db.actualizarUsuario();
            }
        });

        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mandandolo al login de nuevo
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        tv_Borrarcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                //Asegurandose de que el usuario es el que está borrando la cuenta
                builder.setTitle("Eliminando tu cuenta");
                builder.setMessage("Primero valide su identidad.");

                //Solo se puede tener un editText en un alert dialog.
                //Solución es ponerlo en un grupo layout
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText correo = new EditText(getContext());
                correo.setHint("Correo");
                correo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                layout.addView(correo);

                final EditText contraseña = new EditText(getContext());
                contraseña.setHint("Contraseña");
                contraseña.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                layout.addView(contraseña);

                builder.setView(layout);

                builder.setCancelable(true);

                builder.setPositiveButton("Proceder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper db = new DatabaseHelper(getContext());
                        try{
                            //Si presiona que si validar si está bien la info
                            String str_correo=correo.getText().toString();
                            String str_contraseña = contraseña.getText().toString();
                            if (db.validarUsuario(str_correo, str_contraseña)){
                                //Borrar usuario de la base de datos
                                db.borrarUsuario(str_correo);
                                Toast.makeText(getContext(),"Cuenta eliminada", Toast.LENGTH_LONG).show();
                                //lo manda a la pantalla inicial
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(),"Información errónea", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setNegativeButton("No gracias", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Solo cierra el dialogo, no va a borrar cuenta
                        dialogInterface.cancel();
                    }
                });

                //Creando el dialogo
                AlertDialog dialogo = builder.create();
                dialogo.show();
            }
        });
    }
}
