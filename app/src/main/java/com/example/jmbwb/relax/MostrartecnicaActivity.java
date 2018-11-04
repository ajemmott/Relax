package com.example.jmbwb.relax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class MostrartecnicaActivity extends YouTubeBaseActivity {
    TextView tv_descripcion, tv_rating;
    YouTubePlayerView video_youtube;
    RatingBar ratingBar;
    ArrayList<String> favoritos = new ArrayList<>();      //ArrayList con favoritos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrartecnica);
        tv_descripcion = findViewById(R.id.tv_descripcion);
        video_youtube = findViewById(R.id.videoYT);
        ratingBar = findViewById(R.id.ratingBar);
        tv_rating = findViewById(R.id.tv_rating);

        getInfo();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tv_rating.setText("Gracias por su feedback");
                ratingBar.setVisibility(View.INVISIBLE);

                if(getIntent().hasExtra("nombre")){
                    String nombre = getIntent().getStringExtra("nombre");
                    Float rating;
                    rating = ratingBar.getRating();

                    //Busca la posicion de la instancia con ese nombre
                    int i = DatosTecnicas.buscarObj(nombre);
                    //Ya que lo encontró, le pone el rating
                    Fragmento_inicio.datosTecnicas.get(i).setRating(rating);
                }
            }
        });
    }

    private void getInfo(){
        //checkeando si los dos datos fueron pasados para evitar crash
        if(getIntent().hasExtra("nombre") && getIntent().hasExtra("url_video") && getIntent().hasExtra("descripcion")){
            String nombre = getIntent().getStringExtra("nombre");
            final String url_video = getIntent().getStringExtra("url_video");
            String descripcion = getIntent().getStringExtra("descripcion");

            //poniendo la descripcion
            tv_descripcion.setText(descripcion);

            int i = DatosTecnicas.buscarObj(nombre);
            float estrellas = Fragmento_inicio.datosTecnicas.get(i).getRating();
            ratingBar.setRating(estrellas);

            //para el video de youtube
            video_youtube.initialize(YoutubeConfig.getClaveAPI(), new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(url_video);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });

        }
    }

}
