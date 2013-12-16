package com.jamsnap.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jamsnap.R;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends Activity implements MediaPlayer.OnCompletionListener {
    private MediaRecorder recorder;
    private MediaPlayer player;
    private Button start, stop, play;
    File path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.record);

        start = (Button)findViewById(R.id.startButton);
        start.setOnClickListener(startListener);
        stop = (Button)findViewById(R.id.stopButton);
        stop.setOnClickListener(stopListener);
        play = (Button)findViewById(R.id.playButton);
        play.setOnClickListener(playListener);

        recorder = new MediaRecorder();
        path = new File(Environment.getExternalStorageDirectory(), "jamsnap.3gp");

        resetRecorder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        recorder.release();

        if (player != null) {
            player.release();
        }
    }

    private View.OnClickListener startListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                recorder.start();

                start.setEnabled(false);
                stop.setEnabled(true);
            } catch(IllegalStateException e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener stopListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            recorder.stop();
            resetRecorder();

            start.setEnabled(true);
            stop.setEnabled(false);
            play.setEnabled(true);
        }
    };

    View.OnClickListener playListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (player == null) {
                try {
                    player = new MediaPlayer();
                    Log.d("Play", path.getAbsolutePath());
                    player.setDataSource(path.getAbsolutePath());
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                player.stop();
                player.release();
                player = null;
            }
        }
    };

    private void resetRecorder() {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        Log.d("Reset", path.getAbsolutePath());
        recorder.setOutputFile(path.getAbsolutePath());

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        player.release();
        player = null;
    }
}
