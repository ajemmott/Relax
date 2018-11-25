package com.example.jmbwb.relax;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends Activity {
    SurfaceView surfaceview;
    CameraSource cameraSource;
    BarcodeDetector bcdetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        surfaceview = findViewById(R.id.camara);

        bcdetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, bcdetector).setRequestedPreviewSize(640, 480).setAutoFocusEnabled(true).build();

        surfaceview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //Pedir permiso de usar camara
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                try{
                    cameraSource.start(holder);
                }catch (IOException e){
                    Toast.makeText(ScanActivity.this, "?????",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        bcdetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCode = detections.getDetectedItems();

                if (qrCode.size() != 0){
                    //Vibrar para avisar reconocimiento
                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(250);

                    //leyendo la pagina
                    String pagina = qrCode.valueAt(0).displayValue;
                    //cameraSource.stop();

                    //Abrir navegador cuando se detecta string
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(pagina));
                    intent.setPackage("com.android.chrome");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        // Chrome no instalado
                        intent.setPackage(null);
                        startActivity(intent);
                    }
                }
            }
        });
    }

}
