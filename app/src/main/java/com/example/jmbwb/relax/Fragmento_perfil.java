package com.example.jmbwb.relax;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static android.content.Context.MODE_PRIVATE;
//import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;

public class Fragmento_perfil extends Fragment{
    View view;
    EditText et_nombre, et_nedad, et_contraseña;
    TextView tv_Borrarcuenta, tv_borrarHistorial;
    Button btn_guardar, btn_cerrar;
    RadioButton rb_mujer, rb_hombre;

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
        tv_borrarHistorial = view.findViewById(R.id.textView_borrarHistorial);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        btn_cerrar = view.findViewById(R.id.btn_cerrar);
        et_contraseña = view.findViewById(R.id.et_Npassword);
        rb_hombre = view.findViewById(R.id.rb_hombre);
        rb_mujer = view.findViewById(R.id.rb_mujer);

        //Para traer el correo del usuario que viene desde Login
        final String correo = this.getArguments().getString("correo_user");
        //Toast.makeText(getContext(),"Recibí " + correo, Toast.LENGTH_LONG).show();

        //traer de la base de datos y llenarlo en editText
        DatabaseHelper db = new DatabaseHelper(getContext());
        Cursor c = db.obtenerInfo(correo);
        if (c != null){
            et_nombre.setText(c.getString(c.getColumnIndex("nombre")));
            et_nedad.setText(Integer.toString(c.getInt(c.getColumnIndex("edad"))));
            et_contraseña.setText(c.getString(c.getColumnIndex("contraseña")));
            String genero = c.getString(c.getColumnIndex("genero"));
            if (genero.equals(rb_mujer.getText().toString())){
                rb_mujer.setChecked(true);
            }else if (genero.equals(rb_hombre.getText().toString())){
                rb_hombre.setChecked(true);
            }
        }
        c.close();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nuevoNombre = et_nombre.getText().toString();
                final String nuevaContra = et_contraseña.getText().toString();
                String genero = "";
                final int nuevaEdad = Integer.parseInt(et_nedad.getText().toString());

                final DatabaseHelper db = new DatabaseHelper(getContext());

                if (nuevoNombre.isEmpty()) {
                    et_nombre.setError("Introduzca un nombre");
                    et_nombre.requestFocus();
                    return;
                }
                if (nuevaEdad < 0  && nuevaEdad > 110) {
                    et_nedad.setError("Introduzca una edad válida");
                    et_nedad.requestFocus();
                    return;
                }
                if (nuevaContra.length() < 4){
                    et_contraseña.setError("Introduzca una contraseña de más caracteres");
                    et_contraseña.requestFocus();
                    return;
                }
                if(rb_hombre.isChecked()){
                    genero = rb_hombre.getText().toString();
                }else if(rb_mujer.isChecked()){
                    genero = rb_mujer.getText().toString();
                }

                //Preguntar si está seguro
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(nuevoNombre);
                builder.setMessage("¿Estás seguro de los cambios?");
                builder.setCancelable(true);
                final String finalGenero = genero;
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //poner lo que lo actualiza
                        db.actualizarUsuario(nuevoNombre, nuevaContra, nuevaEdad, finalGenero,correo);
                        Toast.makeText(getContext(),"Información Actualizada", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialogo = builder.create();
                dialogo.show();
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
        tv_borrarHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Eliminando historial");
                builder.setMessage("¿Está seguro que desea borrar su historial?");
                builder.setCancelable(true);
                builder.setPositiveButton("Eliminar historial", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper db = new DatabaseHelper(getContext());
                        db.limpiarHistorial(correo);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialogo = builder.create();
                dialogo.show();
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
