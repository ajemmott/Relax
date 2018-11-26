package com.example.jmbwb.relax;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notificacion extends Application {
    public static final String CANAL_1="canal1";

    @Override
    public void onCreate() {
        super.onCreate();

        //Dependiendo del API se necesita un canal
        //Verificando la version del celular. O significa que Oreo api26
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal1 = new NotificationChannel(CANAL_1, "Canal 1", NotificationManager.IMPORTANCE_HIGH);

            canal1.setDescription("Bienvenida");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal1);
        }
    }
}
