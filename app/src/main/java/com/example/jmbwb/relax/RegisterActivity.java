package com.example.jmbwb.relax;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    Button btn_registrar;
    EditText et_nombre, et_edad, et_correo, et_password;
    RadioButton rb_mujer, rb_hombre;
    RadioGroup rg_genero;
    ConstraintLayout constraintLayout;
    NotificationManagerCompat notifMan;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        notifMan = NotificationManagerCompat.from(this); //Para las notificaciones

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

        final Date hoy = Calendar.getInstance().getTime(); //obteniendo el dia de hoy
        final Calendar cal = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener fecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date cumple = cal.getTime(); //el dia de nacimiento

                int edad = Integer.parseInt(sdf.format(hoy)) - Integer.parseInt(sdf.format(cumple)); //calculando edad

                if(!(edad > 110) && !(edad < 0)){
                    et_edad.setText(Integer.toString(edad));
                }else{
                    Snackbar.make(constraintLayout, "Ingrese una fecha válida", Snackbar.LENGTH_LONG).show();
                }
            }
        };

        et_edad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, AlertDialog.THEME_HOLO_DARK, fecha, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

                    if (!TextUtils.isEmpty(nombre) || !TextUtils.isEmpty(correo) || !TextUtils.isEmpty(contraseña) || !TextUtils.isEmpty(genero) || et_edad.getText().toString().length() > 0 || Integer.parseInt(et_edad.getText().toString()) > 110 || Integer.parseInt(et_edad.getText().toString()) <0 ){
                        if (!db.validarUsuario(correo)) {
                            //Preguntar si está seguro
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                            builder.setTitle("Revisemos tus datos primero.");
                            builder.setMessage("Nombre: "+ nombre + "\nCorreo: " + correo + "\nEdad: " + edad + "\nGénero: "+ genero);
                            builder.setCancelable(true);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Usuarios usuario = new Usuarios();
                                    usuario.setNombre(nombre);
                                    usuario.setContraseña(contraseña);
                                    usuario.setCorreo(correo);
                                    usuario.setEdad(edad);
                                    usuario.setGenero(genero);
                                    usuario.setTipo(0);
                                    db.crearUsuario(usuario);

                                    //Para el notification Builder
                                    mandarNotificacion();

                                    //Pasando a login
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
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

    private void mandarNotificacion() {
        // Haciendo notificación
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, Notificacion.CANAL_1)
                    .setSmallIcon(R.drawable.meditation)
                    .setContentTitle("Bienvenido!!")
                    .setContentText("Gracias por unirte a nuestra familia")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
        }

        // Poner como notificacion
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}

