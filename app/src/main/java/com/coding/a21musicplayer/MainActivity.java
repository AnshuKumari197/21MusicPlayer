package com.coding.a21musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    MediaPlayer mediaPlayer;
    AudioManager audioManager;


    public void playMe(View view){
        mediaPlayer.start();
    }

    public void pauseMe(View view){
        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.testaudio);   //yaha sound add kiya

        //get context from Audio service
        // yaha se volume control hoga
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int myMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int myCurrectVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        //Set context to seekbar - volumeRocker
        //yaha pe seekbar set kar rahe jo volume ke liye use hoga
        SeekBar volumeRocker = findViewById(R.id.seekBar);
        volumeRocker.setMax(myMaxVolume);
        volumeRocker.setProgress(myCurrectVolume);

        //Set a listener on volumeRocker
        //yaha se uske progess pe continue update hoga
        volumeRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //TimeLine part of Music App
        //yaha se video ka time line update hoga jitne minute pe hoga video
        final SeekBar timeline = findViewById(R.id.timeline);
        timeline.setMax(mediaPlayer.getDuration());



        //Set onchange listener on Timeline

        timeline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Customize timeline seekbar
        // yaha se video ke sath seek bar ka time automatic work karega
        // ye ni set karne pe jaha drag karege waha se chalega but video ke sath ye run karta ni dikhega
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeline.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);



    }
}