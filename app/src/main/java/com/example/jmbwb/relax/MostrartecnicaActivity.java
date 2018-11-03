package com.example.jmbwb.relax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MostrartecnicaActivity extends YouTubeBaseActivity {
    TextView tv_descripcion;
    YouTubePlayerView video_youtube;
    YouTubePlayer.OnInitializedListener Listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrartecnica);
        tv_descripcion = findViewById(R.id.tv_descripcion);
        video_youtube = findViewById(R.id.videoYT);

        getInfo();
    }

    private void getInfo(){
        //checkeando si los dos datos fueron pasados para evitar crash
        if(getIntent().hasExtra("url_video") && getIntent().hasExtra("descripcion")){
            final String url_video = getIntent().getStringExtra("url_video");
            String descripcion = getIntent().getStringExtra("descripcion");

            //poniendo la descripcion
            tv_descripcion.setText(descripcion);

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
