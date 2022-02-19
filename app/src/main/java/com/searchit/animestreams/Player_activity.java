package com.searchit.animestreams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

public class Player_activity extends AppCompatActivity {

    PlayerView playerViewMain;
    ProgressBar progressBar;
    ImageView btFullScreen;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;
    ImageView speedInc, speedDec;
    TextView speed;
    ArrayList<String> playSpeed;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_activity);
        playerViewMain = findViewById(R.id.movie_exo_player);
        progressBar = findViewById(R.id.progress_bar);
        btFullScreen = playerViewMain.findViewById(R.id.bt_fullscreen);
        speed = findViewById(R.id.speed_text);
        speedInc = findViewById(R.id.exo_inc);
        speedDec = findViewById(R.id.exo_dec);

        playSpeed = new ArrayList<String>();
        playSpeed.add("1f");
        playSpeed.add("1.25f");
        playSpeed.add("1.5f");
        playSpeed.add("1.75f");
        playSpeed.add("2f");


        Intent intent = getIntent();
        String z = intent.getStringExtra("video-url-pass");
        Log.i("hskd", z);
/// Make FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Video url
        Uri videoUrl = Uri.parse(z);
//        Uri videoUrl = Uri.parse("https://streamtape.com/e/ZXAwXkB8ZOSqQp2/shingeki-no-kyojin-season-2-episode-3.mp4");
//        Uri videoUrl = Uri.parse("https://www09.cloud9xx.com/hls/38f59662885b758e560d1cbd61698ee2/ep.203.1623581305.m3u8");

        LoadControl loadControl = new DefaultLoadControl();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector, loadControl);

        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("movie_exo_player");

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null);

        playerViewMain.setPlayer(simpleExoPlayer);

        playerViewMain.setKeepScreenOn(true);

        simpleExoPlayer.prepare(mediaSource);


        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        speedInc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View v) {
                if (Float.parseFloat(speed.getText().toString()) < 2) {
                    double i = Double.parseDouble(speed.getText().toString()) + 0.05;
                    PlaybackParameters param = new PlaybackParameters((float) i);
                    simpleExoPlayer.setPlaybackParameters(param);
                    speed.setText(String.format("%.2f",i));
                }else {
                    Toast.makeText(getApplicationContext(),"Speed cannot be increased anymore",Toast.LENGTH_SHORT).show();
                }
            }
        });


        speedDec.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (Float.parseFloat(speed.getText().toString()) > 1) {
                    double i = Double.parseDouble(speed.getText().toString()) - 0.05;
                    PlaybackParameters param = new PlaybackParameters((float) i);
                    simpleExoPlayer.setPlaybackParameters(param);
                    speed.setText(String.format("%.2f",i));

                }else {
                    Toast.makeText(getApplicationContext(),"Speed cannot be decreased anymore",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    btFullScreen.setImageDrawable(getResources()
                            .getDrawable(R.drawable.ic_fullscreen));

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                } else {
                    btFullScreen.setImageDrawable(getResources()
                            .getDrawable(R.drawable.ic_fullscreen_exit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();

    }


}